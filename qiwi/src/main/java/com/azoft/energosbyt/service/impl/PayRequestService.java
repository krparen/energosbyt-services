package com.azoft.energosbyt.service.impl;

import com.azoft.energosbyt.dto.BasePayCashLkk;
import com.azoft.energosbyt.dto.Command;
import com.azoft.energosbyt.dto.QiwiRequest;
import com.azoft.energosbyt.dto.QiwiResponse;
import com.azoft.energosbyt.entity.QiwiTxnEntity;
import com.azoft.energosbyt.exception.ApiException;
import com.azoft.energosbyt.exception.QiwiResultCode;
import com.azoft.energosbyt.repository.QiwiTxnRepository;
import com.azoft.energosbyt.service.QiwiRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class PayRequestService implements QiwiRequestService {

    /**
     * Используется для форматирования даты при отправке сообщения в очередь pay
     */
    private static final DateTimeFormatter payDateTimeFormatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @Value("${energosbyt.application.qiwi-system-id}")
    private String qiwiSystemId;
    @Value("${energosbyt.rabbit.request.pay.queue-name}")
    private String payRequestQueueName;

    private final AmqpTemplate template;
    private final ObjectMapper mapper;
    private final QiwiTxnRepository qiwiTxnRepository;

    public PayRequestService(AmqpTemplate template, ObjectMapper mapper, QiwiTxnRepository qiwiTxnRepository) {
        this.template = template;
        this.mapper = mapper;
        this.qiwiTxnRepository = qiwiTxnRepository;
    }

    @Override
    public QiwiResponse process(QiwiRequest qiwiRequest) {
        MessageProperties messageProperties = createPayMessageProperties();
        byte[] body = createPayMessageBody(qiwiRequest);
        Message requestMessage = new Message(body, messageProperties);
        template.send(payRequestQueueName, requestMessage);
        return getPayQiwiResponse(qiwiRequest);
    }

    private QiwiResponse getPayQiwiResponse(QiwiRequest qiwiRequest) {
        QiwiResponse response = QiwiResponse.ok();
        response.setOsmp_txn_id(qiwiRequest.getTxn_id());

        QiwiTxnEntity payOperationRecord = qiwiTxnRepository.findByTxnId(qiwiRequest.getTxn_id());
        response.setPrv_txn(payOperationRecord.getId());
        return response;
    }

    private MessageProperties createPayMessageProperties() {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("system_id", qiwiSystemId);
        messageProperties.setHeader("m_guid", UUID.randomUUID().toString());
        messageProperties.setHeader("type", Command.pay.getRabbitType());
        messageProperties.setHeader("m_date",
                LocalDateTime.now().format(payDateTimeFormatter));
        messageProperties.setContentEncoding(StandardCharsets.UTF_8.name());
        return messageProperties;
    }

    private byte[] createPayMessageBody(QiwiRequest qiwiRequest) {

        String bodyAsString = null;
        try {
            bodyAsString = mapper.writeValueAsString(createPayRabbitRequest(qiwiRequest));
        } catch (JsonProcessingException e) {
            String message = "Rabbit request serialization failed";
            log.error(message, e);
            throw new ApiException(message, e, QiwiResultCode.OTHER_PROVIDER_ERROR);
        }
        log.info("body as String: {}", bodyAsString);

        return bodyAsString.getBytes(StandardCharsets.UTF_8);
    }

    private BasePayCashLkk createPayRabbitRequest(QiwiRequest qiwiRequest) {
        BasePayCashLkk cash = new BasePayCashLkk();
        cash.setSystem_id(qiwiSystemId);
        cash.setAccount_id(qiwiRequest.getAccount());
        cash.setAmmount(qiwiRequest.getSum().floatValue());
        cash.setTrx_id(qiwiRequest.getTxn_id());
        cash.setPayDate(dateFromLocalDateTime(qiwiRequest.getTxn_date()));
        return cash;
    }

    private Date dateFromLocalDateTime(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }
}
