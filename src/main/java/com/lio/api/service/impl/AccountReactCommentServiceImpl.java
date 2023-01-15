package com.lio.api.service.impl;

import com.lio.api.model.dto.ReactionDTO;
import com.lio.api.model.entity.AccountReactComment;
import com.lio.api.model.entity.Comment;
import com.lio.api.repository.AccountReactCommentRepository;
import com.lio.api.service.interfaces.AccountReactCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("accountRectCommentService")
public class AccountReactCommentServiceImpl implements AccountReactCommentService {

    private final AccountReactCommentRepository accountReactCommentRepository;

    @Override
    public Boolean deleteAccountReactComment(Integer id) {
        try{
            this.accountReactCommentRepository.deleteById(id);
            return true;
        }catch( Exception e ){
            return false;
        }
    }

    @Autowired
    public AccountReactCommentServiceImpl(
            AccountReactCommentRepository accountReactCommentRepository
    ){
        this.accountReactCommentRepository = accountReactCommentRepository;
    }

    @Override
    public AccountReactComment createAccountComment(ReactionDTO<Comment> reactionDTO) {
        AccountReactComment accountReactComment = AccountReactComment.builder()
                .account(reactionDTO.getAccount())
                .comment(reactionDTO.getTarget())
                .createdDate(LocalDateTime.now())
                .build();
        return this.accountReactCommentRepository.save(accountReactComment);
    }

    @Override
    public AccountReactComment getAccountReactCommentByAccountIdAndCommentId(String accountId, Integer commentId) {
        return this.accountReactCommentRepository.findByAccountIdAndCommentId(
                accountId ,
                commentId
        );
    }

}
