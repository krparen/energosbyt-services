package com.azoft.energosbyt.prebilling.converter.dto.output;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ImportDivision implements InformSystemHolder {

  @JsonProperty("division")
  private List<Division> divisions = new ArrayList<>();

  @Override
  @JsonIgnore
  public String getInformSystem() {
    if (divisions == null || divisions.isEmpty()) {
      return null;
    }

    return divisions.get(0).getInform_system();
  }
}
