package com.lio.api.repository;

import com.lio.api.model.entity.AccountReactPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("accountReactPostRepository")
public interface  AccountReactPostRepository extends JpaRepository<AccountReactPost,Integer> {

    AccountReactPost findByAccountIdAndPostId( String accountId , String postId );
}
