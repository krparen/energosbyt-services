package com.azoft.energosbyt.universal.service;

import com.azoft.energosbyt.universal.dto.MeterValueRequest;
import com.azoft.energosbyt.universal.dto.MeterValueResponse;
import com.azoft.energosbyt.universal.dto.OperationStatus;
import com.azoft.energosbyt.universal.service.queue.PblQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeterValueService {

  @Autowired
  private PblQueueService pblService;

  public MeterValueResponse process(MeterValueRequest request) {

    request.getMvs().forEach(meterValue ->
        pblService.sendMeterValues(request.getSystem(), request.getAccount(), request.getDateMv(), meterValue));

    MeterValueResponse response = new MeterValueResponse();
    response.setStatus(OperationStatus.ok);
    response.setTrx_id(request.getTrx_id());
    return response;
  }
}
