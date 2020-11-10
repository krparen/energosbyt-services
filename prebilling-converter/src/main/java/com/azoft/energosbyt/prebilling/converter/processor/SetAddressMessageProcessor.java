package com.azoft.energosbyt.prebilling.converter.processor;

import com.azoft.energosbyt.dto.rabbit.BaseCcbPremise;
import com.azoft.energosbyt.prebilling.converter.converter.Converter;
import com.azoft.energosbyt.prebilling.converter.dto.output.ImportAddress;
import com.azoft.energosbyt.prebilling.converter.service.RabbitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SetAddressMessageProcessor extends InputMessageProcessor<BaseCcbPremise, ImportAddress> {

  public SetAddressMessageProcessor(Converter<BaseCcbPremise, ImportAddress> converter,
                                    ObjectMapper mapper,
                                    RabbitService rabbitService) {
    super(converter, mapper, rabbitService);
  }

  @Override
  protected Class<BaseCcbPremise> getInputClass() {
    return BaseCcbPremise.class;
  }

  @Override
  protected String getMessageType() {
    return "setAddress";
  }

  @Override
  protected String getOutputEventType() {
    return "Import_Address";
  }
}
