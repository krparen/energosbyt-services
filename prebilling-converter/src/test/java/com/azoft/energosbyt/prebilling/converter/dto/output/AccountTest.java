package com.azoft.energosbyt.prebilling.converter.dto.output;

import static org.junit.jupiter.api.Assertions.*;

import com.azoft.energosbyt.prebilling.converter.BasicSpringBootTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
class AccountTest extends BasicSpringBootTest {

  @Autowired
  private ObjectMapper mapper;

  @Test
  public void testSerialization() throws Exception {
    Account account = new Account();
    account.setBegin_date(LocalDate.now().minusDays(4));
    account.setEnd_date(LocalDate.now());
    account.setNumber("aabd399dk2");

    String accountAsJson = mapper.writeValueAsString(account);
    log.info("account as json: {}", accountAsJson);
  }
}