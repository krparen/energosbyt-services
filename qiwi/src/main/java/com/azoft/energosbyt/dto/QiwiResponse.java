package com.azoft.energosbyt.dto;

import com.azoft.energosbyt.exception.QiwiResultCode;
import com.azoft.energosbyt.serializer.QiwiResponseSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "response")
@JsonSerialize(using = QiwiResponseSerializer.class)
public class QiwiResponse {
  private String osmp_txn_id;
  private Long prv_txn;
  private Integer result;
  private String comment;
  private BigDecimal sum;

  private List<Field> fields;

  public static QiwiResponse ok() {
    QiwiResponse result = new QiwiResponse();
    result.setResult(QiwiResultCode.OK.getNumericCode());
    return result;
  }
}
