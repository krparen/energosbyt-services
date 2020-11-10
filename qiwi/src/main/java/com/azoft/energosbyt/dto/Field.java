package com.azoft.energosbyt.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Field {
  @XmlAttribute
  private String name;
  @XmlAttribute
  private String type;
  @XmlValue
  private String value;
}
