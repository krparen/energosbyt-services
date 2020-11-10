package com.azoft.energosbyt.universal.controller;

import com.azoft.energosbyt.universal.dto.BalanceResponse;
import com.azoft.energosbyt.universal.dto.CheckRequest;
import com.azoft.energosbyt.universal.dto.CheckResponse;
import com.azoft.energosbyt.universal.dto.MeterResponse;
import com.azoft.energosbyt.universal.dto.MeterValueRequest;
import com.azoft.energosbyt.universal.dto.MeterValueResponse;
import com.azoft.energosbyt.universal.dto.PayRequest;
import com.azoft.energosbyt.universal.dto.PayResponse;
import com.azoft.energosbyt.universal.service.BalanceService;
import com.azoft.energosbyt.universal.service.CheckService;
import com.azoft.energosbyt.universal.service.MeterService;
import com.azoft.energosbyt.universal.service.MeterValueService;
import com.azoft.energosbyt.universal.service.PayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(UniversalController.API_URL)
@Slf4j
@RequiredArgsConstructor
public class UniversalController {

    public static final String API_URL = "/api";
    public static final String CHECK_URL = "check";
    public static final String BALANCE_URL = "balance";
    public static final String PAY_URL = "pay";
    public static final String METER_URL = "meter";
    public static final String METER_VALUE_URL = "meter/value";

    private final CheckService checkService;
    private final BalanceService balanceService;
    private final PayService payService;
    private final MeterService meterService;
    private final MeterValueService meterValueService;

    @PostMapping(CHECK_URL)
    public CheckResponse check(@RequestBody @Valid CheckRequest request) {
        return checkService.process(request);
    }

    @GetMapping(BALANCE_URL)
    public BalanceResponse balance(@RequestParam String system, @RequestParam String account) {
        return balanceService.process(system, account);
    }

    @PostMapping(PAY_URL)
    public PayResponse pay(@RequestBody @Valid PayRequest request) {
        return payService.process(request);
    }

    @GetMapping(METER_URL)
    public MeterResponse meter(@RequestParam String system, @RequestParam String account) {
        return meterService.process(system, account);
    }

    @PostMapping(METER_VALUE_URL)
    public MeterValueResponse meterValue(@RequestBody @Valid MeterValueRequest meterValueRequest) {
        return meterValueService.process(meterValueRequest);
    }
}
