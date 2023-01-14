package com.lio.api.repository;

import com.lio.api.model.entity.AccountTweetPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("accountTweetPostRepository")
public interface AccountTweetPostRepository  extends JpaRepository<AccountTweetPost,Integer> {
}
