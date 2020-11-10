package com.azoft.energosbyt.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.Data;

@Data
public class BaseMeter {
    private Srch srch = new Srch();
    private Srch_res srch_res = new Srch_res();

    @Data
    public static class Srch {
        private String person_Id;
        private String account_Number;
        private String contract_Number;
        private String premise_Id;
        private String account_Id;
    }

    @Data
    public static class Srch_res {
        private List<Srch_res_s> serv = new ArrayList<>();

        @Data
        public static class Srch_res_s {
            private String account_id;
            private String serviceType;

            @Data
            public static class Service_point {
                private String premise_id;
                private String sp_id;
                private String serviceType;

                @Data
                public static class Conn_history {
                    private Date start_date;
                    private Date stop_date;
                    private String SaSp;
                    private String hou;
                    private String percent;
                }

                private List<Conn_history> CHs = new ArrayList<>();

                @Data
                public static class Sp_history {
                    private Date install_date;
                    private Date remove_date;
                    private String meter_id;
                    private String m_const;
                }

                private List<Sp_history> SPHs = new ArrayList<>();
            }

            private List<Service_point> SPs = new ArrayList<>();
        }
    }


    private String error_code;
    private String error_message;

    private String system_id;
    private String action;
    private String code;
    private String filial_code;
    private String id;
    private Date allow_expl_date;
    private Date check_date;
    private int check_period;
    private Date remove_date;

    private String load_type;
    private String number;
    private String position_id;
    private String state;
    private String type;
    private String type_desc;


    private String badgeNumber;
    private String meterType;
    private String meterType_desc;
    private String meterStatus;
    private String manufacturer;
    private String manufacturer_desc;
    private String model;
    private String model_desc;
    private String serialNumber;
    private String receivedDate;
    private String longDescription;
    private String meterCharacteristic;
    private String directionMeasurement;
    private String ratedCurrent;
    private String countingMechanism;
    private String ownerMeter;
    private String ownerMeter_desc;
    private String ratedVoltageLinear;
    private String accuracyClass;
    private String diameter;
    private String sealingDate;
    private String actNumber;
    private String faultyMeter;
    private String typeWaterMeters;
    private String reFailure;
    private String calibrationInterval;
    private String ratedVoltagePhase;
    private String sealNumber;
    private String reprogrammingNumber;
    private String verificationDate;
    private String typeAct;
    private String typeMeasuredEnergy;
    private String maximumCurrent;
    private String life;
    private String releaseMeterDate;
    private String meterConfig;
    private String meterConfigurationId;
    private String effectiveDateTime;

    List<Registr> registers = new ArrayList<>();

    @Data
    public static class Registr {
        private String registerId;
        private String seq;
        private String unitOfMeasure;
        private String timeOfUse;
        private String registerConstant;
        private String consumptionType;
        private String howToUse;
        private String numberOfDigitsLeft;
        private String numberOfDigitsRight;
        private String fullScale;
        private String readOutType;
        private String intervalRegisterType;

        public Registr() {
            super();
        }

        @JsonIgnore
        public Registr(String registerId, String seq, String unitOfMeasure, String timeOfUse, String registerConstant, String consumptionType,
                       String howToUse, String numberOfDigitsLeft, String numberOfDigitsRight, String fullScale, String readOutType,
                       String intervalRegisterType) {
            this.registerId = registerId;
            this.seq = seq;
            this.unitOfMeasure = unitOfMeasure;
            this.timeOfUse = timeOfUse;
            this.registerConstant = registerConstant;
            this.consumptionType = consumptionType;
            this.howToUse = howToUse;
            this.numberOfDigitsLeft = numberOfDigitsLeft;
            this.numberOfDigitsRight = numberOfDigitsRight;
            this.fullScale = fullScale;
            this.readOutType = readOutType;
            this.intervalRegisterType = intervalRegisterType;
        }
    }
}
