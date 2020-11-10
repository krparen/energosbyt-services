package com.azoft.energosbyt.prebilling.converter.converter;

import com.azoft.energosbyt.dto.rabbit.BaseMeterValue;
import com.azoft.energosbyt.prebilling.converter.dto.output.Measure;
import com.azoft.energosbyt.prebilling.converter.dto.output.RegisterValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

@Component
public class BaseMeterValueToMeasureConverter implements Converter<BaseMeterValue, Measure> {

  @Override
  public Measure convert(BaseMeterValue input, Map<String, Object> messageHeaders) {
    Measure output = new Measure();
    output.setInform_system(input.getApp_id());
    output.setAccount_number(input.getAccountNumber());
    output.setExt_id_meter(input.getMeterId());
    output.setMeter_number(input.getMeterNumber());
    output.setMeasure_date(input.getMvDate());
    output.setRegister_value(getRegisterValues(input));
    output.setId_source(input.getApp_id());
    output.setId_channel(input.getChannel());
    return output;
  }

  private List<RegisterValue> getRegisterValues(BaseMeterValue input) {
    List<RegisterValue> registerValues = new ArrayList<>();
    if (Strings.isNotBlank(input.getT1())) {
      addRegisterValue(registerValues, input.getT1());
    }

    if (Strings.isNotBlank(input.getT2())) {
      addRegisterValue(registerValues, input.getT2());
    }

    if (Strings.isNotBlank(input.getT3())) {
      addRegisterValue(registerValues, input.getT3());
    }
    return registerValues;
  }

  private void addRegisterValue(List<RegisterValue> registerValues, String value) {
    RegisterValue registerValue = new RegisterValue();
    registerValue.setValue(value);
    registerValue.setExt_id(UUID.randomUUID().toString());
    registerValues.add(registerValue);
  }
}
