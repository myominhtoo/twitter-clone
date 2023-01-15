package com.lio.api.repository;

import com.lio.api.model.entity.AccountReactComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("accountReactCommentRepository")
public interface AccountReactCommentRepository extends JpaRepository<AccountReactComment,Integer> {

    AccountReactComment findByAccountIdAndCommentId( String accountId , Integer commentId );

}
