package com.azoft.energosbyt.prebilling.converter.dto.input;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class BaseCcbMeter implements SystemIdHolder {

    private static final String dateFormat = "yyyy-MM-dd";

    @JsonProperty("system_id")
    String systemId;
    String syncRequestId;
    String meter;
    String badgeNumber;
    String meterType;
    String meterStatus;
    String manufacturer;
    String model;
    String serialNumber;
    String receivedDate;
    ClMeterChars meterCharacteristic;
    ClMeterConfig meterConfig;
    List<ClMeterTest> meterTest = new ArrayList<>();

    @Data
    public static class ClMeterChars{
        String directionMeasurement;
        String ratedCurrent;
        String countingMechanism;
        String ownerMeter;
        String ratedVoltageLinear;
        String accuracyClass;
        String diameter;
        String sealingDate;
        String actNumber;
        String faultyMeter;
        String typeWaterMeters;
        String reFailure;
        Integer calibrationInterval;
        String ratedVoltagePhase;
        String sealNumber;
        String reprogrammingNumber;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateFormat)
        LocalDate verificationDate;

        String typeAct;
        String typeMeasuredEnergy;
        String maximumCurrent;
        String life;
        String releaseMeterDate;
        String effectivDate;
    }

    @Data
    public static class ClMeterConfig{
        String meterConfigurationId;
        String effectiveDateTime;
        List<Clregister> register = new ArrayList<>();

        @Data
        public static class Clregister{
            String registerId;
            String seq;
            String unitOfMeasure;
            String timeOfUse;
            String registerConstant;
            String consumptionType;
            String howToUse;
            String numberOfDigitsLeft;
            String numberOfDigitsRight;
            String fullScale;
            String readOutType;
            String intervalRegisterType;
            String action;

        }
    }

    @Data
    static class ClMeterTest{
        String deviceTestId;
        String testStartDateTime;
        String testStopDateTime;
        String deviceTestStatus;
        String deviceTestType;
        String user;
        String serviceProvider;
        String comments;
        String action;
    }
}
