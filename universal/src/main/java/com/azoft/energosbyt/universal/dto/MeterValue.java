package com.azoft.energosbyt.universal.dto;

import lombok.Data;

@Data
public class MeterValue {
    private String meterId;
    private String meterNumber;
    private String t1;
    private String t2;
    private String t3;
}
