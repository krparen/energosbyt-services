package com.azoft.energosbyt.universal.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BasicResponse {
  private OperationStatus status = OperationStatus.ok;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String errorCode;
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String errorMessage;
}
