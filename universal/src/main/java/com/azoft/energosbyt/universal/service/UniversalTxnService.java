package com.azoft.energosbyt.universal.service;

import com.azoft.energosbyt.universal.entity.UniversalTxnEntity;
import com.azoft.energosbyt.universal.exception.ApiException;
import com.azoft.energosbyt.universal.exception.ErrorCode;
import com.azoft.energosbyt.universal.repository.UniversalTxnRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Slf4j
public class UniversalTxnService {

    private static final String SAME_TXN_RECORD_EXISTS =
            "Transaction with id = %s and system = %s is in progress or finished";

    @Autowired
    private UniversalTxnRepository repository;

    public void assertSameTxnNotExist(String txnId, String system) {

        if (repository.findByTxnIdAndSystem(txnId, system) != null) {
            String message = String.format(SAME_TXN_RECORD_EXISTS, txnId, system);
            log.error(message);
            throw new ApiException(message, ErrorCode.TXN_ID_NOT_UNIQUE, true);
        }
    }

    public void create(String txnId, LocalDateTime txnDate, String system, String account, BigDecimal sum) {
        UniversalTxnEntity newEntity = new UniversalTxnEntity();
        newEntity.setTxnId(txnId);
        newEntity.setTxnDate(txnDate);
        newEntity.setSystem(system);
        newEntity.setAccount(account);
        newEntity.setSum(sum);
        repository.save(newEntity);
    }
}
