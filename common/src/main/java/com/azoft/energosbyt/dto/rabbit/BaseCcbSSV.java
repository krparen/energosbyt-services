package com.azoft.energosbyt.dto.rabbit;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BaseCcbSSV implements SystemIdHolder {
  @JsonProperty("system_id")
  String systemId;
  String syncRequestId;
  String statementConstructId;
  String personId;
  String effectiveStatus;
  String statementAddressSource;
  String mailingPremise;
  String statementRouteType;
  String numberOfCopies;
  String accountNumber;
  String premiseId;
  String division;
  List<StatementConstructDetail> statementConstructDetail = new ArrayList<>();
}

