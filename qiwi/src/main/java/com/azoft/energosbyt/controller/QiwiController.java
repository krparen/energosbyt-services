package com.azoft.energosbyt.controller;

import com.azoft.energosbyt.dto.QiwiRequest;
import com.azoft.energosbyt.dto.QiwiResponse;
import com.azoft.energosbyt.service.RabbitRequestService;
import com.azoft.energosbyt.validator.QiwiRequestValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class QiwiController {

  private final RabbitRequestService rabbitRequestService;
  private final QiwiRequestValidator requestValidator;

  @RequestMapping(value = "/api/checkOrPay", produces = "application/xml;charset=UTF-8")
  public QiwiResponse getOrPay(QiwiRequest request) throws JsonProcessingException {

    requestValidator.validate(request);
    return rabbitRequestService.sendRequestToQueue(request);
  }
}
