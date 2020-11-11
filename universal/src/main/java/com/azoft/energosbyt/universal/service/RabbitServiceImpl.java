package com.azoft.energosbyt.universal.service;

import com.azoft.energosbyt.dto.rabbit.AbstractRabbitDto;
import com.azoft.energosbyt.service.rabbit.RabbitService;
import com.azoft.energosbyt.universal.exception.ApiException;
import com.azoft.energosbyt.universal.exception.ErrorCode;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Service
@Slf4j
public class RabbitServiceImpl implements RabbitService {

    private static final String HEADER_REPLY_TO = "reply-to";
    private static final String RESPONSE_ERROR_DATA_TEMPL =
            "Rabbit response contains error data. errorCode: %s, errorMessage: %s";

    @Value("${energosbyt.rabbit.request.timeout-in-ms}")
    protected Long requestTimeout;

    @Autowired
    protected AmqpTemplate template;
    @Autowired
    protected AmqpAdmin rabbitAdmin;
    @Autowired
    protected ObjectMapper mapper;

    @Override
    public void send(String queueName, MessageProperties messageProperties, Object messageBody) {
        Message personRequestMessage = new Message(toJsonToBytes(messageBody), messageProperties);
        template.send(queueName, personRequestMessage);
    }


    @Override
    public void send(String queueName, Message message) {
        template.send(queueName, message);
    }

    @Override
    public <T> T deserializeBodyAsType(Message message, Class<T> type) {
        String bodyAsString = getMessageBodyAsString(message);
        return safelyDeserializeFromString(bodyAsString, type);
    }

    @Override
    public String getMessageBodyAsString(Message responseMessage) {
        String responseAsString = null;
        try {
            responseAsString = new String(responseMessage.getBody(), StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            String message = "Unsupported encoding for incoming rabbit message";
            log.error(message + "; rabbit message: {}", responseMessage);
            throw new ApiException(message, ErrorCode.UNEXPECTED_ERROR);
        }
        return responseAsString;
    }


    @Override
    public <T extends AbstractRabbitDto> T sendAndReceive(String queueName, MessageProperties messageProperties, T messageBody) {
        String replyQueueName = messageProperties.getHeader(HEADER_REPLY_TO);

        try {
            if (replyQueueName == null || replyQueueName.isEmpty()) {
                replyQueueName = declareReplyQueueWithUuidName();
                messageProperties.setHeader(HEADER_REPLY_TO, replyQueueName);
            }

            Message personRequestMessage = new Message(toJsonToBytes(messageBody), messageProperties);

            template.send(queueName, personRequestMessage);
            T response = (T) safelyReceiveResponse(replyQueueName, messageBody.getClass());

            if (Strings.isNotBlank(response.getError_code()) || Strings.isNotBlank(response.getError_message())) {
                String message = String.format(RESPONSE_ERROR_DATA_TEMPL, response.getError_code(), response.getError_message());
                log.error(message);
                throw new ApiException(message, ErrorCode.UNEXPECTED_ERROR, true);
            }

            return response;
        } finally {
            if (replyQueueName != null) {
                rabbitAdmin.deleteQueue(replyQueueName);
            }
        }
    }

    protected String declareReplyQueueWithUuidName() {
        String replyQueueName = UUID.randomUUID().toString();
        Queue newQueue = new Queue(replyQueueName, false, false, true);

        try {
            return rabbitAdmin.declareQueue(newQueue);
        } catch (AmqpException e) {
            String message = "Queue declaration failed";
            log.error(message, e);
            throw new ApiException(message, e, ErrorCode.UNEXPECTED_ERROR);
        }

    }

    protected byte[] toJsonToBytes(Object bodyObject) {

        String bodyAsString = null;
        try {
            bodyAsString = mapper.writeValueAsString(bodyObject);
        } catch (JsonProcessingException e) {
            String message = "Rabbit request serialization failed";
            log.error(message, e);
            throw new ApiException(message, e, ErrorCode.UNEXPECTED_ERROR);
        }
        log.info("body as String: {}", bodyAsString);

        return bodyAsString.getBytes(StandardCharsets.UTF_8);
    }

    protected <T> T safelyReceiveResponse(String replyQueueName, Class<T> responseClass) {
        Message responseMessage = null;
        try {
            responseMessage = template.receive(replyQueueName, requestTimeout);
        } catch (AmqpException e) {
            String message = "Getting a rabbit response from queue " + replyQueueName + " failed";
            log.error(message, e);
            throw new ApiException(message, e, ErrorCode.UNEXPECTED_ERROR, true);
        }

        if (responseMessage == null) {
            String message = "Rabbit response from queue " + replyQueueName + " failed timeout";
            log.error(message, replyQueueName);
            throw new ApiException(message, ErrorCode.UNEXPECTED_ERROR, true);
        }

        String responseAsString = getMessageBodyAsString(responseMessage);

        T response = null;
        response = safelyDeserializeFromString(responseAsString, responseClass);

        log.info("response from rabbit: {}", response);
        return response;
    }

    private <T> T safelyDeserializeFromString(String responseAsString, Class<T> clazz) {
        T response = null;
        try {
            response = mapper.readValue(responseAsString, clazz);
        } catch (JsonProcessingException e) {
            String message = "Rabbit response deserialization from json failed";
            log.error(message, e);
            throw new ApiException(message, e, ErrorCode.UNEXPECTED_ERROR);
        }

        log.info("deserialized from json: {}", response);
        return response;
    }


}
