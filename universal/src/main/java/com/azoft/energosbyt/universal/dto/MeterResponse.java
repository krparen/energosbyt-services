package com.azoft.energosbyt.universal.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MeterResponse extends BasicResponse {

  private List<Meter> meters;

}
