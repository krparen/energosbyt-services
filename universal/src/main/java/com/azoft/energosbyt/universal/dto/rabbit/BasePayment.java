package com.azoft.energosbyt.universal.dto.rabbit;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BasePayment extends AbstractRabbitDto {
  private String acct_id;
  private float sm;
  private String id;
  private String system_id;
  private String action;
  private Srch srch = new Srch();
  private Srch_res res;

  @Data
  public static class Srch {
    private String account_id;
    private String client_id;
    private String period_from;
    private String period_to;
    private String offset;
    private String limit;
  }

  @Data
  public static class Srch_res {
    private List<Srch_res_pay> srch_res_pay = new ArrayList<>();
    private List<Srch_res_payment> srch_res_payment = new ArrayList<>();
  }

  @Data
  public static class Srch_res_pay {
    private String pay_evt_id;
    private String pay_date;
    private String pay_ammount;
    private List<PayServ> payServ = new ArrayList<>();
  }

  @Data
  public static class Srch_res_payment {
    private String Id;
    private float StartBalance;
    private float Payed;
    private float Accrued;
    private float Consumption;
    private float Cost;
    private String Unit;
    private float IncreaseRatioValue;
    private float IncreaseRatioAmount;
    private float Recalculation;
    private float Benefits;
    private float Penalty;
    private float EndBalance;
    private float PayedInPeriod;
    private float ActualBalance;
    private String Period;
    private String ServId;
    private String ServName;
    private String ServCode;
    private String ServVendorId;
  }

  @Data
  public static class PayServ {
    private String service_type;
    private String ammount;
    private String account_id;
    private String payment_id;
    private String service_type_desc;
  }
}
