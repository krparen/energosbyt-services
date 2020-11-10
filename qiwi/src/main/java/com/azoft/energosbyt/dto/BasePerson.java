package com.azoft.energosbyt.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class BasePerson {

  private String id;
  private String system_id;
  private String action;
  private String error_code;
  private String error_message;

  private String last_name;
  private String first_name;
  private String middle_name;
  private boolean not_middle;
  private Date date_of_birth;
  private String place_of_birth;
  private String sex;

  private List<Email> emails = new ArrayList<>();
  private List<Phone> phones = new ArrayList<>();
  private List<Doc> docs = new ArrayList<>();

  private String is_main_client;
  private String account_rel_type;
  private String account_rel_type_desc;


  private List<Account> accounts = new ArrayList<Account>();
  private List<Premise> premises = new ArrayList<Premise>();

  private String reg_addr;
  private boolean not_addr;
  private String res_addr;

  private Srch_res srch_res = new Srch_res();
  private Srch srch = new Srch();

  @Data
  public static class Email {
    private String id;
    private String type;
    private String email;
    private String contact;
    private String auth;
    private String sequence;
  }

  @Data
  public static class Srch_res {

    @Data
    public static class pers {
      private String id;
      private String fio;
      private String birthDate;
      private String sex;
    }

    private List<pers> res = new ArrayList<>();
  }

  @Data
  public static class Srch {
    private String last_name;
    private String first_name;
    private String middle_name;
    private String account_number;
    private String dept;
    private String contractNumber;
    private String phone;
    private Integer limit;
    private Integer offset;
    private String city;
    private String street;
    private String house;
    private String apartment;
    private String email;
  }

  @Data
  public static class Phone {
    private String id;
    private String type;
    private String phone;
    private String contact;
    private String auth;
    private String sequence;
    private String contactId;
  }

  @Data
  public static class Doc {
    private String seria;
    private String doc_number;
    private String doc_date;
    private String type;
    private String type_desc;
    private String place;
    private String dept;
    private String dept_code;
  }

  @Data
  public static class Account {
    private String account_number;
    private String contract_number;
    private List<AccountServ> accounts = new ArrayList<>();
    private AccountPrem premise;
    private String Division;

    @Data
    public static class AccountServ {
      private String account_id;
      private String customer_class;
      private String status;
      private String status_desc;
      private String end_date;
      private String service_type;
      private String service_type_desc;
      private String acct_service_type;
      private String acct_service_type_desc;
      private String service_prov;
      private String service_prov_desc;
    }

    @Data
    public static class AccountPrem {
      private String premise_id;
      private String postal;
      private String city;
      private String locality;
      private String street;
      private String state;
    }

  }

  @Data
  public static class Premise {
    private String premise_id;
    private String postal;
    private String city;
    private String locality;
    private String street;
    private List<PremiseAcct> accounts = new ArrayList<PremiseAcct>();

    @Data
    public static class PremiseAcct {
      private String account_id;
      private String customer_class;
      private String account_number;
      private String contract_number;
      private String status;
      private String end_date;
      private String service_type;
      private String acct_service_type;
    }
  }

}
