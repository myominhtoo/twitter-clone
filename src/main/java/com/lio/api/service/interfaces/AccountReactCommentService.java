package com.lio.api.service.interfaces;

import com.lio.api.model.dto.ReactionDTO;
import com.lio.api.model.entity.AccountReactComment;
import com.lio.api.model.entity.Comment;

public interface AccountReactCommentService {

    AccountReactComment createAccountComment(ReactionDTO<Comment> reactionDTO);

    AccountReactComment getAccountReactCommentByAccountIdAndCommentId( String accountId , Integer commentId );

    Boolean deleteAccountReactComment( Integer id );

}
