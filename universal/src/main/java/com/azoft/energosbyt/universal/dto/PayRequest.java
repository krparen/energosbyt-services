package com.azoft.energosbyt.universal.dto;

import com.azoft.energosbyt.universal.validation.Money;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class PayRequest {

  private static final String DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm:ss";

  @NotBlank
  private String system;
  @NotBlank
  private String account;
  @NotBlank
  private String trx_id;
  @Money
  private BigDecimal sum;

  @NotNull
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
  private LocalDateTime datePay;

  @Data
  public static class Service {
    private String code;
    private String sum;
  }

}
