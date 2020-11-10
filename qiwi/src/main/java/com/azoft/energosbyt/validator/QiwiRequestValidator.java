package com.azoft.energosbyt.validator;

import com.azoft.energosbyt.dto.Command;
import com.azoft.energosbyt.dto.QiwiRequest;
import com.azoft.energosbyt.exception.ApiException;
import com.azoft.energosbyt.exception.QiwiResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.azoft.energosbyt.dto.QiwiRequest.ACCOUNT_MAX_LENGTH;
import static com.azoft.energosbyt.dto.QiwiRequest.TXN_ID_MAX_LENGTH;

@Component
@Slf4j
public class QiwiRequestValidator {

    public void validate(QiwiRequest request) {

        validateCommand(request.getCommand());

        if (request.getCommand() == Command.check) {
            validateTxnId(request.getTxn_id());
            validateAccount(request.getAccount());
            validateSum(request.getSum());
        }

        if (request.getCommand() == Command.pay) {
            validateTxnId(request.getTxn_id());
            validateAccount(request.getAccount());
            validateSum(request.getSum());
            validateTxnDate(request.getTxn_date());
        }
    }

    private void validateCommand(Command command) {
        if (command == null) {
            String message = "command should be not null";
            log.error(message);
            throw new ApiException(message, QiwiResultCode.OTHER_PROVIDER_ERROR, true);
        }
    }

    private void validateTxnId(String txnId) {
        if (txnId == null || txnId.isEmpty() || txnId.length() > TXN_ID_MAX_LENGTH) {
            String message = "txn_id should be not empty and up to " + TXN_ID_MAX_LENGTH + " characters";
            log.error(message);
            throw new ApiException(message, QiwiResultCode.OTHER_PROVIDER_ERROR, true);
        }
    }

    private void validateAccount(String account) {
        if (account == null || account.isEmpty() || account.length() > ACCOUNT_MAX_LENGTH) {
            String message = "account should be not empty and up to " + ACCOUNT_MAX_LENGTH + " characters";
            log.error(message);
            throw new ApiException(message, QiwiResultCode.OTHER_PROVIDER_ERROR, true);
        }
    }

    private void validateSum(BigDecimal sum) {
        if (sum == null || sum.compareTo(BigDecimal.ZERO) < 0) {
            String message = "sum should be not null and positive";
            log.error(message);
            throw new ApiException(message, QiwiResultCode.OTHER_PROVIDER_ERROR, true);
        }
    }

    private void validateTxnDate(LocalDateTime txnDate) {
        if (txnDate == null) {
            String message = "txn_date should be not null";
            log.error(message);
            throw new ApiException(message, QiwiResultCode.OTHER_PROVIDER_ERROR, true);
        }
    }
}
