package com.azoft.energosbyt.prebilling.converter.converter;

import com.azoft.energosbyt.dto.rabbit.BaseCcbPremise;
import com.azoft.energosbyt.prebilling.converter.dto.output.Address;
import com.azoft.energosbyt.prebilling.converter.dto.output.ImportAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseCcbPremiseToImportAddressConverter implements Converter<BaseCcbPremise, ImportAddress> {

  @Autowired
  private BaseCcbPremiseToAddressConverter baseCcbPremiseToAddressConverter;

  @Override
  public ImportAddress convert(BaseCcbPremise input, Map<String, Object> messageHeaders) {

    Address address = baseCcbPremiseToAddressConverter.convert(input, messageHeaders);
    List<Address> addresses = new ArrayList<>();
    addresses.add(address);

    ImportAddress result = new ImportAddress();
    result.setAddresses(addresses);

    return result;
  }

}
