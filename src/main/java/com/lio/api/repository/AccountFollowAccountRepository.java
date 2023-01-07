package com.lio.api.repository;

import com.lio.api.model.entity.Account;
import com.lio.api.model.entity.AccountFollowAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("accountFollowAccountRepo")
public interface AccountFollowAccountRepository extends
        JpaRepository<AccountFollowAccount,Integer> {

    @Query(
            value = "SELECT * FROM account_follow_account t " +
            "WHERE t.from_account_id = :accountId " ,
            nativeQuery = true
    )
    List<AccountFollowAccount> findByFromAccountId( @Param("accountId") String accountId );

    @Query(
            value = "SELECT * FROM account_follow_account t " +
                    "WHERE t.to_account_id = :accountId ",
            nativeQuery = true
    )
    List<AccountFollowAccount> findByToAccountId( @Param("accountId") String accountId);

    @Query(
            value = "SELECT * FROM account_follow_account t " +
                    "WHERE t.from_account_id = :fromId AND t.to_account_id = :toId ",
            nativeQuery = true
    )
    AccountFollowAccount findByFromAccountIdAndToAccountId(
            @Param("fromId") String fromId,
            @Param("toId") String toId
    );

}
