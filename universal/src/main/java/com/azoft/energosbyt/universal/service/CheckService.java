package com.azoft.energosbyt.universal.service;

import com.azoft.energosbyt.universal.dto.rabbit.BasePerson;
import com.azoft.energosbyt.universal.dto.CheckRequest;
import com.azoft.energosbyt.universal.dto.CheckResponse;
import com.azoft.energosbyt.universal.service.queue.CcbQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CheckService {

  @Autowired
  private CcbQueueService ccbQueueService;
  @Autowired
  private UniversalTxnService txnService;

  public CheckResponse process(CheckRequest request) {

    txnService.assertSameTxnNotExist(request.getTrx_id(), request.getSystem());

    BasePerson personSearch = ccbQueueService.searchPerson(request.getAccount());
    String personId = personSearch.getSrch_res().getRes().get(0).getId();
    String address = ccbQueueService.getAddress(personId, request.getAccount());

    CheckResponse response = new CheckResponse();
    response.setAddress(address);
    return response;
  }
}
