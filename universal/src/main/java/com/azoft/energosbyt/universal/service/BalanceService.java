package com.azoft.energosbyt.universal.service;

import com.azoft.energosbyt.universal.dto.BalanceResponse;
import com.azoft.energosbyt.universal.dto.rabbit.BasePayment;
import com.azoft.energosbyt.universal.service.queue.CcbQueueService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

  @Autowired
  private CcbQueueService ccbService;

  public BalanceResponse process(String system, String account) {

    BasePayment basePayment = ccbService.getBalance(account);

    BalanceResponse response = new BalanceResponse();
    response.setBalance(BigDecimal.valueOf(basePayment.getSm()).setScale(2, RoundingMode.HALF_UP));
    return response;
  }
}
