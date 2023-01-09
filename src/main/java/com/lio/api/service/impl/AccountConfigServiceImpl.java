package com.lio.api.service.impl;

import com.lio.api.exception.custom.Index;
import com.lio.api.model.entity.Account;
import com.lio.api.model.entity.AccountConfigurations;
import com.lio.api.repository.AccountConfigRepository;
import com.lio.api.service.interfaces.AccountConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.lio.api.model.constant.Messages.DUPLICATE_ACCOUNT_CONFIG;
import static com.lio.api.model.constant.Messages.INVALID_REQUEST;

@Service("accountConfigService")
public class AccountConfigServiceImpl implements AccountConfigService {

    private final AccountConfigRepository accountConfigRepo;

    @Autowired
    public AccountConfigServiceImpl( AccountConfigRepository accountConfigRepo ){
        this.accountConfigRepo = accountConfigRepo;
    }

    @Override
    public AccountConfigurations configureAccount(
            String accountId ,
            AccountConfigurations accountConfigurations
    ) throws Index.InvalidRequestException {
        AccountConfigurations savedConfig = this.accountConfigRepo.findByAccountId( accountId );

        if( !accountId.equals(accountConfigurations.getAccount().getId()) ||
                savedConfig == null
        ){
            throw new Index.InvalidRequestException( INVALID_REQUEST );
        }
        accountConfigurations.setUpdatedDate(LocalDateTime.now());
        accountConfigurations.setId(savedConfig.getId());
        return this.accountConfigRepo.save( accountConfigurations );
    }

    @Override
    public AccountConfigurations getAccountConfiguration(String accountId)
        throws Index.InvalidRequestException {
        AccountConfigurations accountConfigurations = this.accountConfigRepo.findByAccountId( accountId );
        if( accountConfigurations == null ){
            throw new Index.InvalidRequestException( INVALID_REQUEST );
        }
        return accountConfigurations;
    }

    @Override
    public AccountConfigurations createAccountConfiguration(Account account)
            throws Index.DuplicateAccountConfigurationException {

        if( this.accountConfigRepo.findByAccountId(account.getId()) != null ){
            throw new Index.DuplicateAccountConfigurationException( DUPLICATE_ACCOUNT_CONFIG );
        }

        return this.accountConfigRepo.save(
                this.getDefaultAccountConfiguration(account)
        );
    }

    @Override
    public AccountConfigurations getDefaultAccountConfiguration(Account account){
        AccountConfigurations accountConfigurations = AccountConfigurations.builder()
                .account(account)
                .createdDate(LocalDateTime.now())
                .isMuteRingtone(false)
                .showFollowers(true)
                .showFollowings(true)
                .isLockProfile(false)
                .showNotification(true)
                .updatedDate(null)
                .build();
        return accountConfigurations;
    }
}
