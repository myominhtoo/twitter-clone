package com.lio.api.service.impl;

import java.util.List;

import com.lio.api.model.dto.ReactionDTO;
import com.lio.api.model.dto.RetweetPostDTO;
import com.lio.api.model.entity.Post;
import com.lio.api.service.interfaces.PostService;

public class PostServiceImpl implements PostService {

    @Override
    public Post createTweet(Post tweet) {
        return null;
    }

    @Override
    public Boolean deleteTweet(String tweetId) {
        return null;
    }

    @Override
    public Post editTweet(Post tweet) {
        return null;
    }

    @Override
    public List<Post> getTweets() {
        return null;
    }

    @Override
    public Boolean reactTweet(ReactionDTO<Post> reactionDTO) {
        return null;
    }

    @Override
    public Boolean retweetPost(RetweetPostDTO retweetPostDTO) {
        return null;
    }
    
}
