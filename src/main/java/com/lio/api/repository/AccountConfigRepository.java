package com.lio.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lio.api.model.entity.AccountConfigurations;

@Repository("accountConfigRepository")
public interface AccountConfigRepository extends JpaRepository<AccountConfigurations,Integer> {

    @Query( value = "select * from account_configurations t where t.account_id = ? " , nativeQuery = true )
    AccountConfigurations findByAccountId( String accountId );

}
