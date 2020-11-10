package com.azoft.energosbyt.prebilling.converter.processor;

import com.azoft.energosbyt.prebilling.converter.converter.Converter;
import com.azoft.energosbyt.prebilling.converter.dto.input.BaseCcbMeter;
import com.azoft.energosbyt.prebilling.converter.dto.output.ImportMeter;
import com.azoft.energosbyt.prebilling.converter.service.RabbitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class SetMeterMessageProcessor extends InputMessageProcessor<BaseCcbMeter, ImportMeter> {

    protected SetMeterMessageProcessor(
            Converter<BaseCcbMeter, ImportMeter> converter,
            ObjectMapper mapper,
            RabbitService rabbitService) {
        super(converter, mapper, rabbitService);
    }

    @Override
    protected Class<BaseCcbMeter> getInputClass() {
        return BaseCcbMeter.class;
    }

    @Override
    protected String getMessageType() {
        return "setMeter";
    }

    @Override
    protected String getOutputEventType() {
        return "Import_Meter";
    }
}
