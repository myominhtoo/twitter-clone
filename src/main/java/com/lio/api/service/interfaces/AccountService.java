package com.lio.api.service.interfaces;

import java.util.List;

import com.lio.api.exception.custom.Index;
import com.lio.api.model.dto.AccountFollowDTO;
import com.lio.api.model.entity.Account;

public interface AccountService {

    Account createAccount( Account account  )
            throws Index.DuplicateAccountException;
    
    Boolean isDuplicateAccount( Account account );

    List<Account> getAllAccounts();

    Account login( Account account )
            throws Index.InvalidRequestException;

    void sendVerification( String email );

    Account editAccount( String accountId , Account account )
            throws Index.InvalidRequestException ;

    Boolean followAccount( AccountFollowDTO accountFollowDTO )
            throws Index.InvalidRequestException;

    Boolean unfollowAccount( AccountFollowDTO accountFollowDTO )
            throws Exception;

    Boolean verifyAccount( String accountId , String email , Integer verificationCode );

}
