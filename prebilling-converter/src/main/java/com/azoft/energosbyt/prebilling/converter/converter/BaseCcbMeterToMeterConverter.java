package com.azoft.energosbyt.prebilling.converter.converter;

import com.azoft.energosbyt.prebilling.converter.dto.input.BaseCcbMeter;
import com.azoft.energosbyt.prebilling.converter.dto.output.Meter;
import com.azoft.energosbyt.prebilling.converter.dto.output.Register;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class BaseCcbMeterToMeterConverter extends AbstractConverter<BaseCcbMeter, Meter> {

    @Override
    public Meter convert(BaseCcbMeter input, Map<String, Object> messageHeaders) {
        Meter output = new Meter();
        output.setInform_system(getInformSystem(input, messageHeaders));
        output.setExt_id(input.getMeter());
        output.setName(input.getModel());
        output.setNumber(input.getSerialNumber());
        output.setTariff_zone(input.getMeterConfig().getRegister().size());
        output.setRemove_date(getRemoveDate(input));
        output.setRegister(getRegisters(input));
        return output;
    }

    private List<Register> getRegisters(BaseCcbMeter input) {

        List<BaseCcbMeter.ClMeterConfig.Clregister> inputRegisters = input.getMeterConfig().getRegister();
        List<Register> outputRegisters = new ArrayList<>();

        for (int i = 0; i < inputRegisters.size(); i++) {
            Register register = new Register();
            register.setPosition(i + 1);
            register.setExt_id_register(inputRegisters.get(i).getRegisterId());
            register.setDigit_capacity(inputRegisters.get(i).getNumberOfDigitsLeft());
            register.setUnit(inputRegisters.get(i).getUnitOfMeasure());
            register.setIs_empty(getIsEmpty(inputRegisters.get(i).getConsumptionType()));
            outputRegisters.add(register);
        }

        return outputRegisters;
    }

    private Boolean getIsEmpty(String consumptionType) {
        return !"S".equals(consumptionType);
    }

    private LocalDate getRemoveDate(BaseCcbMeter input) {

        if (input.getMeterCharacteristic() == null) {
            return null;
        }

        LocalDate verificationDate = input.getMeterCharacteristic().getVerificationDate();
        Integer calibrationInterval = input.getMeterCharacteristic().getCalibrationInterval();

        if (verificationDate == null || calibrationInterval == null) {
            return null;
        }

        return verificationDate.plusYears(calibrationInterval);
    }
}
