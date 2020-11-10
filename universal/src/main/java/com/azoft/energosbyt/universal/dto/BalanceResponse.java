package com.azoft.energosbyt.universal.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BalanceResponse extends BasicResponse {
  private BigDecimal balance;
  private List<Service> services;

  @Data
  public static class Service {
    private String code;
    private String name;
    private BigDecimal balance;
  }
}
