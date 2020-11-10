package com.azoft.energosbyt.prebilling.converter.converter;

import com.azoft.energosbyt.prebilling.converter.dto.input.BaseCcbSSV;
import com.azoft.energosbyt.prebilling.converter.dto.output.Account;
import com.azoft.energosbyt.prebilling.converter.dto.output.ImportAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class BaseCcbSSVToImportAccountConverter implements Converter<BaseCcbSSV, ImportAccount> {

  @Autowired
  private BaseCcbSSVToAccountConverter baseCcbSSVToAccountConverter;

  @Override
  public ImportAccount convert(BaseCcbSSV input, Map<String, Object> messageHeaders) {

    Account account = baseCcbSSVToAccountConverter.convert(input, messageHeaders);
    List<Account> accounts = new ArrayList<>();
    accounts.add(account);

    ImportAccount result = new ImportAccount();
    result.setAccounts(accounts);

    return result;
  }

}
