package com.azoft.energosbyt.service.impl;

import com.azoft.energosbyt.dto.BasePayment;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
//@EnableRabbit //нужно для активации обработки аннотаций @RabbitListener
//@Component
public class MockRabbitListener {

  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private RabbitTemplate rabbitTemplate;

  @RabbitListener(queues = "queue1")
  private void sendMockResponse(Message message) throws Exception {

    String replyToQueueName = message.getMessageProperties().getReplyTo();

    String bodyAsString = new String(message.getBody());
    BasePayment requestBody = objectMapper.readValue(bodyAsString, BasePayment.class);
    log.info("Object from message : {}", requestBody);

    Thread.sleep(2000);

    MessageProperties messageProperties = new MessageProperties();
    messageProperties.setContentEncoding(StandardCharsets.UTF_8.name());

    String responseBodyAsString = objectMapper.writeValueAsString(createMockResponse(requestBody));
    byte[] responseBodyAsBytes = responseBodyAsString.getBytes(StandardCharsets.UTF_8);
    Message responseMessage = new Message(responseBodyAsBytes, messageProperties);
    rabbitTemplate.send(replyToQueueName, responseMessage);

  }

  private BasePayment createMockResponse(BasePayment request) {
    BasePayment payment = new BasePayment();
    payment.setAcct_id(request.getSrch().getAccount_id());
    payment.setAction("delete");
    payment.setSm(30);
    return payment;
  }
}
