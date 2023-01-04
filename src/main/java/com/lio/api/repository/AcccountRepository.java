package com.lio.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lio.api.model.entity.Account;

@Repository
public interface AcccountRepository extends JpaRepository<Account,String> {
    
    Account findByEmail( String email );

    Account findByIdAndEmail( String id , String email );

}
