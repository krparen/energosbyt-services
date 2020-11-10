package com.azoft.energosbyt.service.impl;

import com.azoft.energosbyt.repository.QiwiTxnRepository;
import com.azoft.energosbyt.dto.*;
import com.azoft.energosbyt.entity.QiwiTxnEntity;
import com.azoft.energosbyt.exception.ApiException;
import com.azoft.energosbyt.exception.QiwiResultCode;
import com.azoft.energosbyt.service.RabbitRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class RabbitRequestServiceImpl implements RabbitRequestService {

    private static final String TXN_RECORD_WITH_SAME_ID_EXISTS =
            "Транзакция с id = %s уже в обработке или завершена";

    private final QiwiTxnRepository qiwiTxnRepository;
    private final CheckRequestService checkRequestService;
    private final PayRequestService payRequestService;

    public RabbitRequestServiceImpl(QiwiTxnRepository qiwiTxnRepository, CheckRequestService checkRequestService, PayRequestService payRequestService) {
        this.qiwiTxnRepository = qiwiTxnRepository;
        this.checkRequestService = checkRequestService;
        this.payRequestService = payRequestService;
    }

    @Override
    @Transactional
    public QiwiResponse sendRequestToQueue(QiwiRequest qiwiRequest) {

        QiwiTxnEntity txnWithSameId = qiwiTxnRepository.findByTxnId(qiwiRequest.getTxn_id());
        if (txnWithSameId == null) {
            if (qiwiRequest.getCommand() == Command.pay) {
                createTxnRecord(qiwiRequest);
            }
        } else {
            return txnRecordExistsResponse(qiwiRequest);
        }


        if (qiwiRequest.getCommand() == Command.check) {
            return checkRequestService.process(qiwiRequest);
        } else {
            return payRequestService.process(qiwiRequest);
        }

    }

    private QiwiResponse txnRecordExistsResponse(QiwiRequest qiwiRequest) {
        QiwiResponse qiwiResponse = new QiwiResponse();
        qiwiResponse.setResult(QiwiResultCode.OTHER_PROVIDER_ERROR.getNumericCode());

        String comment = String.format(TXN_RECORD_WITH_SAME_ID_EXISTS, qiwiRequest.getTxn_id());
        qiwiResponse.setComment(comment);

        return qiwiResponse;
    }

    private void createTxnRecord(QiwiRequest qiwiRequest) {
        QiwiTxnEntity newTxnRecord = new QiwiTxnEntity();
        newTxnRecord.setTxnId(qiwiRequest.getTxn_id());
        newTxnRecord.setTxnDate(qiwiRequest.getTxn_date());
        newTxnRecord.setCommand(qiwiRequest.getCommand());
        newTxnRecord.setAccount(qiwiRequest.getAccount());
        newTxnRecord.setSum(qiwiRequest.getSum());
        qiwiTxnRepository.save(newTxnRecord);
    }
}
