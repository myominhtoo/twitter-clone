package com.lio.api.service.interfaces;

import java.util.List;

import com.lio.api.exception.custom.Index;
import com.lio.api.model.dto.AccountFollowDTO;
import com.lio.api.model.entity.Account;

public interface AccountService {

    Account createAccount( Account account  ) throws Index.DuplicateAccountException;
    
    Boolean isDuplicateAccount( Account account );

    List<Account> getAllAccounts();

    Account login( Account account );

    void sendVerification( String email );

    Account editAccount( Account account );

    Boolean followAccount( AccountFollowDTO accountFollowDTO );

    Boolean unfollowAccount( AccountFollowDTO accountFollowDTO );

    Boolean verifyAccount( String accountId , String email , Integer verificationCode );

}
