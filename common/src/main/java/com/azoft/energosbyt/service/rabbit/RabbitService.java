package com.azoft.energosbyt.service.rabbit;

import com.azoft.energosbyt.dto.rabbit.AbstractRabbitDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

public interface RabbitService {

    <T extends AbstractRabbitDto> T sendAndReceive(
            String queueName, MessageProperties messageProperties, T messageBody);

    void send(String queueName, MessageProperties messageProperties, Object messageBody);

    void send (String queueName, Message message);

    <T> T deserializeBodyAsType(Message message, Class<T> type);

    String getMessageBodyAsString(Message responseMessage);
}
