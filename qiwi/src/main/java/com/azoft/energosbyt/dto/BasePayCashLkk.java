package com.azoft.energosbyt.dto;

import lombok.Data;
import java.util.Date;

@Data
public class BasePayCashLkk {
    private String system_id;
    private String client_id;
    private String account_id;
    private String transaction_id;
    private String trx_id;
    private float ammount;
    private Date payDate;
}
