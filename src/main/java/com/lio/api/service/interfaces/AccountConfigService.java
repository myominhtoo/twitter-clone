package com.lio.api.service.interfaces;

import com.lio.api.exception.custom.Index;
import com.lio.api.model.entity.Account;
import com.lio.api.model.entity.AccountConfigurations;

public interface AccountConfigService {
    
    AccountConfigurations configureAccount( String accountId , AccountConfigurations accountConfiguration )
            throws Index.InvalidRequestException;

    AccountConfigurations getAccountConfiguration( String accountId )
            throws Index.InvalidRequestException;

    AccountConfigurations createAccountConfiguration(Account account)
            throws Index.DuplicateAccountConfigurationException;

    AccountConfigurations getDefaultAccountConfiguration(Account account);

}
