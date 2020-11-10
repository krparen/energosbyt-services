package com.azoft.energosbyt.prebilling.converter.dto.output;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Division {
  private String inform_system;
  private String ext_id;
  private String name;
  private String ext_id_company;
  @JsonProperty("district")
  private List<District> districts = new ArrayList<>();
}
