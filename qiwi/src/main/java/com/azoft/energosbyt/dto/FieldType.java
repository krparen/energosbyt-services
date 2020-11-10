package com.azoft.energosbyt.dto;

import lombok.Getter;

public enum FieldType {
    DISP("disp"),
    INFO("info"),
    PRT_DATA("prt-data");

    @Getter
    private final String stringValue;

    FieldType(String stringValue) {
        this.stringValue = stringValue;
    }
}
