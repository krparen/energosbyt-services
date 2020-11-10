package com.azoft.energosbyt.universal.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Meter {
  private String meterId;
  private String meterNumber;
  private String serviceName;
  private String digits;

  @JsonProperty("T1")
  private String t1;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("T2")
  private String t2;

  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonProperty("T3")
  private String t3;
}
