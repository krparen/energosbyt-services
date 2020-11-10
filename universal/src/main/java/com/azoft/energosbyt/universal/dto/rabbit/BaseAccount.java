package com.azoft.energosbyt.universal.dto.rabbit;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BaseAccount extends AbstractRabbitDto {
    private String id;
    private String system_id;
    private String action;
    private boolean printAccount;
    private AccountData accountData;

    @Data
    public static class AccountData {
        private String accountServiceType;
        private String serviceType;
        private String serviceProvider;
        private String stopPayDate;
        private String stopOverpayDate;
        private String delivery_type;
        private String delivery_address;
    }
}