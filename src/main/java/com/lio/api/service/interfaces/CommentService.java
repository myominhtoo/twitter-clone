package com.lio.api.service.interfaces;

import java.util.List;

import com.lio.api.exception.custom.Index;
import com.lio.api.model.dto.ReactionDTO;
import com.lio.api.model.entity.Comment;

public interface CommentService {
    
    Comment createComment( Comment comment ) throws Index.InvalidRequestException;

    List<Comment> getTweetComments( String tweetId );

    Comment editComment( Comment comment ) throws Index.InvalidRequestException;

    Comment getComment( Integer commentId  ) throws Index.InvalidRequestException;

    Boolean deleteComment( Integer commentId );

    Boolean reactComment( ReactionDTO<Comment> reactionDTO ) throws Index.InvalidRequestException;

}
