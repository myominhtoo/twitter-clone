package com.lio.api.service.impl;

import com.lio.api.model.entity.AccountReactPost;
import com.lio.api.repository.AccountReactPostRepository;
import com.lio.api.service.interfaces.AccountReactPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("accountReactPostService")
public class AccountReactPostServiceImpl implements AccountReactPostService {

    private final AccountReactPostRepository accountReactPostRepository;

    @Autowired
    public AccountReactPostServiceImpl( AccountReactPostRepository accountReactPostRepository ){
        this.accountReactPostRepository = accountReactPostRepository;
    }

    @Override
    public AccountReactPost createReactionToPost(AccountReactPost accountReactPost) {
        accountReactPost.setCreatedDate(LocalDateTime.now());
        return this.accountReactPostRepository.save(accountReactPost);
    }

    @Override
    public AccountReactPost getReactionByAccountAndPost(String accountId, String postId) {
       return this.accountReactPostRepository
               .findByAccountIdAndPostId( accountId , postId );
    }

    @Override
    public void removeReactionToPost(Integer id) {
        this.accountReactPostRepository.deleteById(id);
    }
}

