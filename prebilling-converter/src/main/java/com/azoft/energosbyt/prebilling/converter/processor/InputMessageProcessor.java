package com.azoft.energosbyt.prebilling.converter.processor;

import com.azoft.energosbyt.prebilling.converter.converter.Converter;
import com.azoft.energosbyt.prebilling.converter.dto.output.InformSystemHolder;
import com.azoft.energosbyt.prebilling.converter.dto.output.wrapper.ConverterResult;
import com.azoft.energosbyt.prebilling.converter.dto.output.wrapper.ResultWrapper;
import com.azoft.energosbyt.prebilling.converter.service.RabbitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Slf4j
public abstract class InputMessageProcessor<I, O> {

  @Value("${energosbyt.rabbit.output-queue}")
  protected String outputQueueName;

  protected final Converter<I, O> converter;
  protected final ObjectMapper mapper;
  private final RabbitService rabbitService;


  protected InputMessageProcessor(Converter<I, O> converter, ObjectMapper mapper, RabbitService rabbitService) {
    this.converter = converter;
    this.mapper = mapper;
    this.rabbitService = rabbitService;
  }

  protected abstract Class<I> getInputClass();

  protected abstract String getMessageType();

  protected abstract String getOutputEventType();

  public void process(Message inputMessage) {

    I input = rabbitService.deserializeBodyAsType(inputMessage, getInputClass());

    MessageProperties outputProperties = new MessageProperties();
    for (Map.Entry<String, Object> entry : inputMessage.getMessageProperties().getHeaders().entrySet()) {
      outputProperties.setHeader(entry.getKey(), entry.getValue());
    }

    O converted = converter.convert(input, inputMessage.getMessageProperties().getHeaders());

    ConverterResult<O> output = constructResult(converted);

    rabbitService.send(outputQueueName, outputProperties, output);
  }

  private ConverterResult<O> constructResult(O converted) {

    ResultWrapper<O> result = new ResultWrapper<>();
    result.setGuid(UUID.randomUUID());
    result.setCommand(getOutputEventType());
    result.setCreationDate(LocalDateTime.now());
    result.setData(converted);
    setInformSystem(converted, result);

    return ConverterResult.of(result);
  }

  private void setInformSystem(O converted, ResultWrapper<O> result) {
    if (converted instanceof InformSystemHolder) {
      InformSystemHolder informSystemHolder = ((InformSystemHolder) converted);
      result.setInformSystem(informSystemHolder.getInformSystem());
    } else {
      result.setInformSystem(null);
    }
  }

  public boolean appliesTo (String messageType) {
    return getMessageType().equals(messageType);
  }
}
