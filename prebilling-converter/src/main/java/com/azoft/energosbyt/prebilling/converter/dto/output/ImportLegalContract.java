package com.azoft.energosbyt.prebilling.converter.dto.output;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImportLegalContract {

  @JsonProperty("legal_contract")
  private List<LegalContract> legalContracts = new ArrayList<>();
}
