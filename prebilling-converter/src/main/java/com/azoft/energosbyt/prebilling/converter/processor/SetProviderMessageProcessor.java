package com.azoft.energosbyt.prebilling.converter.processor;

import com.azoft.energosbyt.prebilling.converter.converter.Converter;
import com.azoft.energosbyt.prebilling.converter.dto.input.BaseCcbProvider;
import com.azoft.energosbyt.prebilling.converter.dto.output.ImportProvider;
import com.azoft.energosbyt.prebilling.converter.service.RabbitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class SetProviderMessageProcessor extends InputMessageProcessor<BaseCcbProvider, ImportProvider> {

    protected SetProviderMessageProcessor(Converter<BaseCcbProvider, ImportProvider> converter,
                                          ObjectMapper mapper,
                                          RabbitService rabbitService) {
        super(converter, mapper, rabbitService);
    }

    @Override
    protected Class<BaseCcbProvider> getInputClass() {
        return BaseCcbProvider.class;
    }

    @Override
    protected String getMessageType() {
        return "setProvider";
    }

    @Override
    protected String getOutputEventType() {
        return "Import_Provider";
    }
}
