package com.azoft.energosbyt.universal.service.queue;

import com.azoft.energosbyt.universal.dto.rabbit.BasePayCashLkk;
import com.azoft.energosbyt.universal.service.RabbitService;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Service
public class PayQueueService {

    private static final String TYPE_PAY = "setPayLkk";

    /**
     * Используется для форматирования даты при отправке сообщения в очередь pay
     */
    private static final DateTimeFormatter payDateTimeFormatter =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @Value("${energosbyt.rabbit.request.pay.queue-name}")
    private String payQueueName;

    @Autowired
    private RabbitService rabbitService;

    public void process(String systemId, String account, BigDecimal sum, String txnId, LocalDateTime txnDate) {
        MessageProperties messageProperties = createMessageProperties(TYPE_PAY, systemId);
        BasePayCashLkk bodyObject = createPayRabbitRequest(systemId, account, sum, txnId, txnDate);
        rabbitService.send(payQueueName, messageProperties, bodyObject);
    }


    private BasePayCashLkk createPayRabbitRequest(String systemId, String account, BigDecimal sum, String txnId,
                                                  LocalDateTime txnDate) {
        BasePayCashLkk cash = new BasePayCashLkk();
        cash.setSystem_id(systemId);
        cash.setAccount_id(account);
        cash.setAmmount(sum.floatValue());
        cash.setTrx_id(txnId);
        cash.setPayDate(dateFromLocalDateTime(txnDate));
        return cash;
    }

    protected MessageProperties createMessageProperties(String type, String systemId) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("system_id", systemId);
        messageProperties.setHeader("m_guid", UUID.randomUUID().toString());
        messageProperties.setHeader("type", type);
        messageProperties.setHeader("m_date", LocalDateTime.now().format(payDateTimeFormatter));
        messageProperties.setContentEncoding(StandardCharsets.UTF_8.name());
        return messageProperties;
    }

    private Date dateFromLocalDateTime(LocalDateTime dateToConvert) {
        return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }
}
