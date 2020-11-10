package com.azoft.energosbyt.universal.service.queue;

import com.azoft.energosbyt.universal.dto.rabbit.*;
import com.azoft.energosbyt.universal.exception.ApiException;
import com.azoft.energosbyt.universal.exception.ErrorCode;
import com.azoft.energosbyt.universal.service.RabbitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class CcbQueueService {

    private static final String TYPE_SEARCH_PERSON = "searchPerson";
    private static final String TYPE_SEARCH_METER = "searchMeter";
    private static final String TYPE_GET_METER = "getMeter";
    private static final String TYPE_GET_PERSON_ACCOUNT = "getPersAccount";
    private static final String TYPE_GET_PREMISE = "getPremise";
    private static final String TYPE_GET_BALANCE = "getHdBalance";
    private static final String TYPE_GET_ACCOUNT_INFO = "getAccInfo";

    @Value("${energosbyt.rabbit.request.check.queue-name}")
    private String ccbQueueName;
    @Value("${energosbyt.application.this-system-id}")
    protected String thisSystemId;

    @Autowired
    private RabbitService rabbitService;

    public BaseAccount getAccount(String accountId, String system) {
        MessageProperties properties = createMessageProperties(TYPE_GET_ACCOUNT_INFO);
        BaseAccount bodyObject = createGetAccountInfoRabbitRequest(accountId, system);
        BaseAccount response = rabbitService.sendAndReceive(ccbQueueName, properties, bodyObject);
        return response;
    }

    private BaseAccount createGetAccountInfoRabbitRequest(String accountId, String system) {
        BaseAccount account = new BaseAccount();
        account.setId(accountId);
        account.setSystem_id(system);
        return account;
    }

    public BasePerson searchAccounts(String personId) {
        MessageProperties properties = createMessageProperties(TYPE_GET_PERSON_ACCOUNT);
        BasePerson bodyObject = createSearchAccountRabbitRequest(personId);
        BasePerson response = rabbitService.sendAndReceive(ccbQueueName, properties, bodyObject);
        log.info("response for searchAccount: {}", response);
        return response;
    }

    public String getAddress(String personId, String account) {
        BasePerson accountsSearchResult = searchAccounts(personId);

        String premiseId = accountsSearchResult.getAccounts().stream()
                .filter(acc -> account.equals(acc.getAccount_number()))
                .findFirst()
                .map(BasePerson.Account::getPremise)
                .map(BasePerson.Account.AccountPrem::getPremise_id)
                .orElse(null);

        if (premiseId == null) {
            return null;
        }

        BasePremise prem = getPremise(premiseId);
        String address = (prem.getPostal() + " " + prem.getCity() + " " + prem.getCounty() +
                " " + prem.getStreet() + " " + prem.getHouse() + "-" + prem.getApartment())
                .replaceAll("null", "")
                .replaceAll(" ", " ");
        return address;
    }

    public BasePremise getPremise(String premiseId) {
        MessageProperties properties = createMessageProperties(TYPE_GET_PREMISE);
        BasePremise bodyObject = createGetPremiseRabbitRequest(premiseId);
        BasePremise response = rabbitService.sendAndReceive(ccbQueueName, properties, bodyObject);
        log.info("response for getPremise: {}", response);
        return response;
    }

    private BasePremise createGetPremiseRabbitRequest(String premiseId) {
        BasePremise premise = new BasePremise();
        premise.setId(premiseId);
        return premise;
    }

    public BasePerson searchPerson(String account) {

        MessageProperties messageProperties = createMessageProperties(TYPE_SEARCH_PERSON);
        BasePerson bodyObject = createSearchPersonByAccountRabbitRequest(account);
        BasePerson personRabbitResponse = rabbitService.sendAndReceive(ccbQueueName, messageProperties, bodyObject);

        if (personRabbitResponse.getSrch_res().getRes().isEmpty()) {
            String message = "No person found for account id = " + account;
            log.error(message);
            throw new ApiException(message, ErrorCode.ACCOUNT_NOT_FOUND, true);
        }

        if (personRabbitResponse.getSrch_res().getRes().size() > 1) {
            String message = "More than one person found for account id = " + account;
            log.error(message);
            throw new ApiException(message, ErrorCode.UNEXPECTED_ERROR, true);
        }

        return personRabbitResponse;
    }

    public BaseMeter searchMeters(String personId) {
        MessageProperties messageProperties = createMessageProperties(TYPE_SEARCH_METER);
        BaseMeter bodyObject = createMetersRabbitRequest(personId);
        return rabbitService.sendAndReceive(ccbQueueName, messageProperties, bodyObject);
    }

    public BaseMeter getMeter(String meterId) {
        MessageProperties metersMessageProperties = createMessageProperties(TYPE_GET_METER);
        BaseMeter bodyObject = createMeterValuesRabbitRequest(meterId);
        return rabbitService.sendAndReceive(ccbQueueName, metersMessageProperties, bodyObject);
    }

    public BasePayment getBalance(String account) {
        BasePerson personSearch = searchPerson(account);
        String personId = personSearch.getSrch_res().getRes().get(0).getId();
        MessageProperties messageProperties = createMessageProperties(TYPE_GET_BALANCE);
        BasePayment balanceSearchBody = createGetBalanceRabbitRequest(account, personId);
        return rabbitService.sendAndReceive(ccbQueueName, messageProperties, balanceSearchBody);
    }

    private BaseMeter createMeterValuesRabbitRequest(String meterId) {
        BaseMeter rabbitRequest = new BaseMeter();
        rabbitRequest.setSystem_id(thisSystemId);
        rabbitRequest.setId(meterId);
        return rabbitRequest;
    }

    private BaseMeter createMetersRabbitRequest(String personId) {
        BaseMeter rabbitRequest = new BaseMeter();
        rabbitRequest.setSystem_id(thisSystemId);

        BaseMeter.Srch search = new BaseMeter.Srch();
        search.setPerson_Id(personId);
        rabbitRequest.setSrch(search);
        return rabbitRequest;
    }

    private BasePerson createSearchPersonByAccountRabbitRequest(String account) {
        BasePerson rabbitRequest = new BasePerson();
        rabbitRequest.setSystem_id(thisSystemId);

        BasePerson.Srch search = new BasePerson.Srch();
        search.setAccount_number(account);
        search.setDept("ORESB");
        rabbitRequest.setSrch(search);
        return rabbitRequest;
    }

    private BasePerson createSearchAccountRabbitRequest(String personId) {
        BasePerson basePerson = new BasePerson();
        basePerson.setId(personId);
        return basePerson;
    }

    private BasePayment createGetBalanceRabbitRequest(String account, String personId) {
        BasePayment payment = new BasePayment();
        payment.setAcct_id(account);
        payment.getSrch().setAccount_id(account);
        payment.getSrch().setClient_id(personId);
        return payment;
    }

    protected MessageProperties createMessageProperties(String type) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("type", type);
        messageProperties.setHeader("m_guid", "08.06.2020"); // легаси заголовок, должен присутствовать, а что в нём - не важно
        messageProperties.setContentEncoding(StandardCharsets.UTF_8.name());
        return messageProperties;
    }
}
