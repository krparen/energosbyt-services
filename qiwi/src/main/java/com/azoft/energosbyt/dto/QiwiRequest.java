package com.azoft.energosbyt.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class QiwiRequest {

  private static final String dateTimeFormat = "yyyyMMddHHmmss";
  public static final int TXN_ID_MAX_LENGTH = 20;
  public static final int ACCOUNT_MAX_LENGTH = 200;

  private Command command;
  private String txn_id;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = dateTimeFormat, timezone = "Europe/Moscow") // отвечает за сериализацию
  @DateTimeFormat(pattern = dateTimeFormat) // отвечает за десериализацию
  private LocalDateTime txn_date;
  private String account;
  private BigDecimal sum;
}
