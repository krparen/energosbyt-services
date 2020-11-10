package com.azoft.energosbyt.universal.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CheckRequest {

  @NotBlank
  private String system;
  @NotBlank
  private String account;
  @NotBlank
  private String trx_id;
  private BigDecimal sum;
  private List<Service> services;

  @Data
  public static class Service {
    private String code;
    private BigDecimal sum;
  }
}
