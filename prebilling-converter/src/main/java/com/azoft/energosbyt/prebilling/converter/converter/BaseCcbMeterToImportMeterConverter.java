package com.azoft.energosbyt.prebilling.converter.converter;

import com.azoft.energosbyt.prebilling.converter.dto.input.BaseCcbMeter;
import com.azoft.energosbyt.prebilling.converter.dto.output.ImportMeter;
import com.azoft.energosbyt.prebilling.converter.dto.output.Meter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class BaseCcbMeterToImportMeterConverter implements Converter<BaseCcbMeter, ImportMeter> {

    @Autowired
    private BaseCcbMeterToMeterConverter baseCcbMeterToMeterConverter;

    @Override
    public ImportMeter convert(BaseCcbMeter input, Map<String, Object> messageHeaders) {
        Meter meter = baseCcbMeterToMeterConverter.convert(input, messageHeaders);

        ImportMeter importMeter = new ImportMeter();

        List<Meter> meters = new ArrayList<>();
        meters.add(meter);

        importMeter.setMeters(meters);
        return importMeter;
    }
}
