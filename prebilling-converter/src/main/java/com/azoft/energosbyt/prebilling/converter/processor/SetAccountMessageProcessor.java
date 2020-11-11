package com.azoft.energosbyt.prebilling.converter.processor;

import com.azoft.energosbyt.dto.rabbit.BaseCcbSSV;
import com.azoft.energosbyt.prebilling.converter.converter.Converter;
import com.azoft.energosbyt.prebilling.converter.dto.output.ImportAccount;
import com.azoft.energosbyt.service.rabbit.RabbitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class SetAccountMessageProcessor extends InputMessageProcessor<BaseCcbSSV, ImportAccount> {

  public SetAccountMessageProcessor(Converter<BaseCcbSSV, ImportAccount> converter,
                                    ObjectMapper mapper,
                                    RabbitService rabbitService) {
    super(converter, mapper, rabbitService);
  }

  @Override
  protected String getOutputEventType() {
    return "Import_Account";
  }

  @Override
  protected Class<BaseCcbSSV> getInputClass() {
    return BaseCcbSSV.class;
  }

  @Override
  protected String getMessageType() {
    return "setAccount";
  }
}
