package com.lio.api.service.impl;

import java.util.List;

import com.lio.api.model.dto.ReactionDTO;
import com.lio.api.model.entity.Comment;
import com.lio.api.service.interfaces.CommentService;

public class CommentServiceImpl implements CommentService {

    @Override
    public Comment createComment(Comment comment) {
        return null;
    }

    @Override
    public Boolean deleteComment(Integer commentId) {
        return null;
    }

    @Override
    public Comment editComment(Comment comment) {
        return null;
    }

    @Override
    public List<Comment> getTweetComments(String tweetId) {
        return null;
    }

    @Override
    public Boolean reactComment(ReactionDTO<Comment> reactionDTO) {
        return null;
    }
    
}
