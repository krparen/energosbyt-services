package com.azoft.energosbyt.prebilling.converter.converter;

import com.azoft.energosbyt.dto.rabbit.SystemIdHolder;
import com.azoft.energosbyt.prebilling.converter.service.ReferenceQueryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static com.azoft.energosbyt.prebilling.converter.common.RabbitHeaders.HEADER_SYSTEM_ID;

public abstract class AbstractConverter<I, O> implements Converter<I, O> {

    @Autowired
    protected ReferenceQueryService referenceQueryService;


    protected String getInformSystem(I input, Map<String, Object> messageHeaders) {

        String systemId = null;

        if (messageHeaders.get(HEADER_SYSTEM_ID) != null) {
            systemId = (String) messageHeaders.get(HEADER_SYSTEM_ID);
        }

        if (systemId == null && input instanceof SystemIdHolder) {
            systemId = ((SystemIdHolder)input).getSystemId();
        }

        return referenceQueryService.getInformSystemCode(systemId);
    }
}
