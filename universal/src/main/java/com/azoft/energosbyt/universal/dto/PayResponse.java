package com.azoft.energosbyt.universal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PayResponse extends BasicResponse {
  private String trx_id;
}
