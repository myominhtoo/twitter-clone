package com.lio.api.service.interfaces;

import java.util.List;

import com.lio.api.model.dto.AccountFollowDTO;
import com.lio.api.model.entity.Account;

public interface AccountService {
    
    Boolean isDuplicateAccount( Account account );

    List<Account> getAllAccounts();

    Account login( Account account );

    void sendVerification( String email );

    Account editAccount( Account account );

    Boolean followAccount( AccountFollowDTO accountFollowDTO );

    Boolean unfollowAccount( AccountFollowDTO accountFollowDTO );

}
