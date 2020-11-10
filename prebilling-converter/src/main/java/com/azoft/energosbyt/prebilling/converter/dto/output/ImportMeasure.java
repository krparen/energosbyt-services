package com.azoft.energosbyt.prebilling.converter.dto.output;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImportMeasure {

  @JsonProperty("measure")
  private List<Measure> measures = new ArrayList<>();
}
