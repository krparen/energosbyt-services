package com.azoft.energosbyt.prebilling.converter.dto.input;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

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

