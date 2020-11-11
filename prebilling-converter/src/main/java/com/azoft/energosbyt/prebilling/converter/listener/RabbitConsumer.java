package com.azoft.energosbyt.prebilling.converter.listener;

import com.azoft.energosbyt.prebilling.converter.exception.ApiException;
import com.azoft.energosbyt.prebilling.converter.exception.ErrorCode;
import com.azoft.energosbyt.prebilling.converter.processor.InputMessageProcessor;
import com.azoft.energosbyt.service.rabbit.RabbitService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Message;

@Component
@EnableRabbit
@Slf4j
public class RabbitConsumer {

  private static final String TYPE_HEADER = "type";

  @Value("${energosbyt.rabbit.error-queue}")
  private String ERROR_QUEUE_NAME;

  @Autowired
  private List<InputMessageProcessor> messageProcessors;
  @Autowired
  private RabbitService rabbitService;

  @RabbitListener(queues = "${energosbyt.rabbit.input-queue}")
  public void listen (Message message) {

    try {
      log.info("processing message with type " + message.getMessageProperties().getHeader(TYPE_HEADER));
      String messageType = message.getMessageProperties().getHeader(TYPE_HEADER);
      InputMessageProcessor processor = chooseMessageProcessor(messageType);

      if (processor == null) {
        log.error("Message with unknown type {} received, skipping it. headers: {}, body: {}",
            messageType, message.getMessageProperties().getHeaders(), rabbitService.getMessageBodyAsString(message));
        return;
      }

      processor.process(message);
    } catch (Exception e) {
      log.error("Exception happened. Sending message to error queue. Exception: ", e);
      rabbitService.send(ERROR_QUEUE_NAME, message);
    }
  }

  private InputMessageProcessor chooseMessageProcessor(String messageType) {

    List<InputMessageProcessor> validProcessors = messageProcessors.stream()
        .filter(processor -> processor.appliesTo(messageType))
        .collect(Collectors.toList());

    if (validProcessors.isEmpty()) {
      return null;
    }

    if (validProcessors.size() > 1) {
      String message = "More than one processor for message type " + messageType;
      log.error(message);
      throw new ApiException(message, ErrorCode.OPERATION_PROCESSING_ERROR, true);
    }

    return validProcessors.get(0);
  }
}
