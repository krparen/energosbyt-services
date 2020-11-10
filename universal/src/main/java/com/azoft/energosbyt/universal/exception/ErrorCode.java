package com.azoft.energosbyt.universal.exception;

import lombok.Getter;

public enum ErrorCode {
  INCORRECT_SYSTEM_CODE("001"),
  ACCOUNT_NOT_FOUND("002"),
  AMOUNT_CANNOT_BE_ACCEPTED("003"),
  TXN_ID_NOT_UNIQUE("004"),
  METER_NOT_FOUND("005"),
  METER_DATA_PROCESSING_ERROR("006"),
  UNEXPECTED_ERROR("010");

  @Getter
  private String stringValue;

  ErrorCode(String stringValue) {
    this.stringValue = stringValue;
  }
}
