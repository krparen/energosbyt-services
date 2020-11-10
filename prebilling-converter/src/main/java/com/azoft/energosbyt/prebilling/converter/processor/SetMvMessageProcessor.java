package com.azoft.energosbyt.prebilling.converter.processor;

import com.azoft.energosbyt.dto.rabbit.BaseMeterValue;
import com.azoft.energosbyt.prebilling.converter.converter.Converter;
import com.azoft.energosbyt.prebilling.converter.dto.output.ImportMeasure;
import com.azoft.energosbyt.prebilling.converter.service.RabbitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class SetMvMessageProcessor extends InputMessageProcessor<BaseMeterValue, ImportMeasure> {

  protected SetMvMessageProcessor(Converter<BaseMeterValue, ImportMeasure> converter,
                                  ObjectMapper mapper,
                                  RabbitService rabbitService) {
    super(converter, mapper, rabbitService);
  }

  @Override
  protected Class<BaseMeterValue> getInputClass() {
    return BaseMeterValue.class;
  }

  @Override
  protected String getMessageType() {
    return "setMv";
  }

  @Override
  protected String getOutputEventType() {
    return "Import_Measure";
  }
}
