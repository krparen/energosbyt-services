package com.azoft.energosbyt.prebilling.converter.dto.output;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImportService {

  @JsonProperty("service")
  private List<ServiceDto> services;
}
