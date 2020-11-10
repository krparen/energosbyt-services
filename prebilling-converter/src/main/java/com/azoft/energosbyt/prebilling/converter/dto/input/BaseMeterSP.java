package com.azoft.energosbyt.prebilling.converter.dto.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BaseMeterSP implements SystemIdHolder {
  @JsonProperty("system_id")
  String systemId;
  String id;
  String personalAccountId;
  String objectId;
  String accountType;
  String status;
}
