package com.azoft.energosbyt.prebilling.converter.processor;

import com.azoft.energosbyt.dto.rabbit.BaseOffice;
import com.azoft.energosbyt.prebilling.converter.converter.Converter;
import com.azoft.energosbyt.prebilling.converter.dto.output.ImportDivision;
import com.azoft.energosbyt.prebilling.converter.service.RabbitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class SetOfficeMessageProcessor extends InputMessageProcessor<BaseOffice, ImportDivision> {

    protected SetOfficeMessageProcessor(Converter<BaseOffice, ImportDivision> converter,
                                        ObjectMapper mapper,
                                        RabbitService rabbitService) {
        super(converter, mapper, rabbitService);
    }

    @Override
    protected Class<BaseOffice> getInputClass() {
        return BaseOffice.class;
    }

    @Override
    protected String getMessageType() {
        return "setOffice";
    }

    @Override
    protected String getOutputEventType() {
        return "Import_Division";
    }
}
