package com.azoft.energosbyt.universal.service;

import com.azoft.energosbyt.universal.dto.OperationStatus;
import com.azoft.energosbyt.universal.dto.PayRequest;
import com.azoft.energosbyt.universal.dto.PayResponse;
import com.azoft.energosbyt.universal.service.queue.PayQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PayService {

  @Autowired
  private UniversalTxnService txnService;
  @Autowired
  private PayQueueService payQueueService;

  @Transactional
  public PayResponse process(PayRequest request) {

    txnService.assertSameTxnNotExist(request.getTrx_id(), request.getSystem());

    txnService.create(request.getTrx_id(), request.getDatePay(), request.getSystem(),
            request.getAccount(), request.getSum());

    payQueueService.process(request.getSystem(), request.getAccount(), request.getSum(),
            request.getTrx_id(), request.getDatePay());

    PayResponse response = new PayResponse();
    response.setStatus(OperationStatus.ok);
    response.setTrx_id(request.getTrx_id());
    return response;
  }
}
