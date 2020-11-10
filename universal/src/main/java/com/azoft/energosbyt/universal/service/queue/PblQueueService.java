package com.azoft.energosbyt.universal.service.queue;

import com.azoft.energosbyt.universal.dto.MeterValue;
import com.azoft.energosbyt.universal.dto.rabbit.BaseMeterValue;
import com.azoft.energosbyt.universal.service.RabbitService;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PblQueueService {

  private static final String TYPE_SEND_METER_VALUES = "setMv";
  private static final String PBL_QUEUE_NAME = "pbl";

  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

  @Autowired
  private RabbitService rabbitService;

  public void sendMeterValues(String system, String account, LocalDateTime mvDate, MeterValue meterValue) {
    BaseMeterValue bodyObject = createSendMeterValuesRabbitRequest(account, meterValue.getMeterId(), meterValue.getMeterNumber(),
        meterValue.getT1(), meterValue.getT2(), meterValue.getT3(), mvDate);
    MessageProperties messageProperties = createMessageProperties(TYPE_SEND_METER_VALUES, system);
    rabbitService.send(PBL_QUEUE_NAME, messageProperties, bodyObject);
  }

  private BaseMeterValue createSendMeterValuesRabbitRequest(String account, String meterId, String meterNumber,
                                                            String t1, String t2, String t3, LocalDateTime mvDate) {
    BaseMeterValue bmv = new BaseMeterValue();
    bmv.setAccountNumber(account);
    bmv.setMeterId(meterId);
    bmv.setMeterNumber(meterNumber);
    bmv.setT1(t1);
    bmv.setT2(t2);
    bmv.setT3(t3);
    bmv.setMvDate(mvDate);

    return bmv;
  }

  protected MessageProperties createMessageProperties(String type, String systemId) {
    MessageProperties messageProperties = new MessageProperties();
    messageProperties.setHeader("system_id", systemId);
    messageProperties.setHeader("m_guid", UUID.randomUUID().toString());
    messageProperties.setHeader("type", type);
    messageProperties.setHeader("m_date", LocalDateTime.now().format(DATE_TIME_FORMATTER));
    messageProperties.setContentEncoding(StandardCharsets.UTF_8.name());
    return messageProperties;
  }

  private Date dateFromLocalDateTime(LocalDateTime dateToConvert) {
    return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
  }
}
