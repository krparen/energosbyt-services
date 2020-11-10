package com.azoft.energosbyt.universal.service;

import com.azoft.energosbyt.universal.dto.rabbit.BaseAccount;
import com.azoft.energosbyt.universal.dto.rabbit.BaseMeter;
import com.azoft.energosbyt.universal.dto.rabbit.BasePerson;
import com.azoft.energosbyt.universal.dto.Meter;
import com.azoft.energosbyt.universal.dto.MeterResponse;
import com.azoft.energosbyt.universal.service.queue.CcbQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
public class MeterService {

    private final CcbQueueService ccbQueueService;

    public MeterService(CcbQueueService ccbQueueService) {
        this.ccbQueueService = ccbQueueService;
    }

    public MeterResponse process(String system, String account) {
        BasePerson personRabbitResponse = ccbQueueService.searchPerson(account);
        String personId = personRabbitResponse.getSrch_res().getRes().get(0).getId();

        BaseMeter metersRabbitResponse = ccbQueueService.searchMeters(personId);
        log.info("User with id {} has meters {}", personId, metersRabbitResponse.getSrch_res().getServ());

        Map<String, String> activeMetersIdAndAccountId = getActiveMetersIdAndAccountId(metersRabbitResponse);
        Map<String, String> meterIdToServiceType = getMeterIdAndServiceType(system, activeMetersIdAndAccountId);

        List<BaseMeter> activeMeters = getActiveMeters(activeMetersIdAndAccountId.keySet());
        return getMeterResponse(activeMeters, meterIdToServiceType);
    }

    private Map<String, String> getMeterIdAndServiceType(String system, Map<String, String> activeMetersIdAndAccountId) {
        Map<String, String> meterIdToServiceType = new HashMap<>();

        for (Map.Entry<String, String> entry : activeMetersIdAndAccountId.entrySet()) {
             BaseAccount accountInfo = ccbQueueService.getAccount(entry.getValue(), system); // ищем информацию об аккаунте по его id

            // далее вытаскиваем serviceType из инфо об аккаунте
            String serviceType = Optional.ofNullable(accountInfo)
                    .map(BaseAccount::getAccountData)
                    .map(BaseAccount.AccountData::getServiceType)
                    .map(s -> s.substring(s.indexOf("|")+1))
                    .orElse(null);

            meterIdToServiceType.put(entry.getKey(), serviceType); // и делаем новую map meterId -> serviceType
        }

        return meterIdToServiceType;
    }

    private List<BaseMeter> getActiveMeters(Set<String> activeMeterIds) {
        List<BaseMeter> activeMeters = new ArrayList<>();
        activeMeterIds.forEach(id -> {
            BaseMeter activeMeter = ccbQueueService.getMeter(id);
            activeMeters.add(activeMeter);
        });
        return activeMeters;
    }

    private Map<String, String> getActiveMetersIdAndAccountId(BaseMeter metersSearchResult) {
        Map<String, String> activeMetersIdAndServiceType = new HashMap<>();

        List<BaseMeter.Srch_res.Srch_res_s> services = metersSearchResult.getSrch_res().getServ();
        for (BaseMeter.Srch_res.Srch_res_s service : services) {
            List<BaseMeter.Srch_res.Srch_res_s.Service_point> servicePoints = service.getSPs();
            for (BaseMeter.Srch_res.Srch_res_s.Service_point servicePoint : servicePoints) {
                List<BaseMeter.Srch_res.Srch_res_s.Service_point.Conn_history> connectionHistories = servicePoint.getCHs();
                boolean isOpenConnectionExists = false;
                for (BaseMeter.Srch_res.Srch_res_s.Service_point.Conn_history connectionHistory : connectionHistories) {
                    if (connectionHistory.getStop_date() == null) {
                        isOpenConnectionExists = true;
                        break;
                    }
                }

                if (isOpenConnectionExists) {
                    List<BaseMeter.Srch_res.Srch_res_s.Service_point.Sp_history> spHistories = servicePoint.getSPHs();
                    for (BaseMeter.Srch_res.Srch_res_s.Service_point.Sp_history spHistory : spHistories) {
                        if (spHistory.getRemove_date() == null) {
                            activeMetersIdAndServiceType.put(spHistory.getMeter_id(), service.getAccount_id());
                        }
                    }
                }
            }
        }

        return activeMetersIdAndServiceType;
    }

    private MeterResponse getMeterResponse(List<BaseMeter> activeMeters, Map<String, String> meterIdToServiceType) {

        List<Meter> meters = new ArrayList<>();

        activeMeters.forEach(baseMeter -> {
            Meter meter = new Meter();
            meter.setMeterId(baseMeter.getId());
            meter.setMeterNumber(baseMeter.getBadgeNumber());
            meter.setServiceName(meterIdToServiceType.get(baseMeter.getId()));

            List<BaseMeter.Registr> registrs = baseMeter.getRegisters();

            if (!registrs.isEmpty()) {
                meter.setDigits(registrs.get(0).getNumberOfDigitsLeft());
            }

            meter.setT1("1.0");

            meters.add(meter);
        });

        MeterResponse response = new MeterResponse();
        response.setMeters(meters);

        return response;
    }

}
