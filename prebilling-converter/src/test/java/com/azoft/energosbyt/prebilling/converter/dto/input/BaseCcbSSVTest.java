package com.azoft.energosbyt.prebilling.converter.dto.input;

import com.azoft.energosbyt.prebilling.converter.BasicSpringBootTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class BaseCcbSSVTest extends BasicSpringBootTest {

  @Autowired
  private ObjectMapper mapper;

  private static final String baseCcbSsvJson = "{\"system_id\":\"Oracle\",\"syncRequestId\":\"80327166880556\",\"statementConstructId\":\"A.5928343902\",\"personId\":\"9722335860\",\"effectiveStatus\":\"A\",\"statementAddressSource\":null,\"mailingPremise\":null,\"statementRouteType\":null,\"numberOfCopies\":null,\"accountNumber\":\"900650732\",\"premiseId\":\"6022413763\",\"division\":\"SESB\",\"statementConstructDetail\":[{\"constructDetailId\":\"5928343902_20200315\",\"accountId\":\"5928343902\",\"startDate\":\"2020-03-15\",\"endDate\":null,\"office\":\"ZA-A\",\"district\":\"ZA-A-06\"}]}";

  @Test
  void deserializationTest() throws Exception {
    BaseCcbSSV baseCcbSSV = mapper.readValue(baseCcbSsvJson, BaseCcbSSV.class);
    log.info("deserialized class: {}", baseCcbSSV);
  }

}