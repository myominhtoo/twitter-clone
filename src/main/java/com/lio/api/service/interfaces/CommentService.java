package com.lio.api.service.interfaces;

import java.util.List;

import com.lio.api.model.dto.ReactionDTO;
import com.lio.api.model.entity.Comment;

public interface CommentService {
    
    Comment createComment( Comment comment );

    List<Comment> getTweetComments( String tweetId );

    Comment editComment( Comment comment );

    Boolean deleteComment( Integer commentId );

    Boolean reactComment( ReactionDTO<Comment> reactionDTO );

}
