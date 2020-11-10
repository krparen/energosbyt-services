package com.azoft.energosbyt.prebilling.converter.converter;

import com.azoft.energosbyt.prebilling.converter.dto.input.BaseCcbSSV;
import com.azoft.energosbyt.prebilling.converter.dto.output.ImportServiceProvider;
import com.azoft.energosbyt.prebilling.converter.dto.output.ServiceProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class BaseCcbSSVToImportServiceProviderConverter
    implements Converter<BaseCcbSSV, ImportServiceProvider> {

  @Override
  public ImportServiceProvider convert(BaseCcbSSV input, Map<String, Object> messageHeaders) {
    ServiceProvider serviceProvider = new ServiceProvider();
    // TODO: запрлнить serviceProvider
    List<ServiceProvider> serviceProviders = new ArrayList<>();
    serviceProviders.add(serviceProvider);


    ImportServiceProvider result = new ImportServiceProvider();
    result.setServiceProviders(serviceProviders);
    return result;
  }
}
