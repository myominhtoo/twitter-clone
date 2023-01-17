package com.lio.api.service.interfaces;

import java.util.List;

import com.lio.api.exception.custom.Index;
import com.lio.api.model.dto.ReactionDTO;
import com.lio.api.model.dto.RetweetPostDTO;
import com.lio.api.model.entity.Post;
import com.lio.api.model.entity.PostConfigurations;

public interface PostService {
    
    List<Post> getTweets();

    Post createTweet( Post tweet ) throws Index.InvalidRequestException;

    Post getTweet( String postId ) throws Index.InvalidRequestException;

    Post editTweet( String tweetId , Post tweet ) throws Index.InvalidRequestException;

    Boolean deleteTweet( String accountId , String tweetId ) throws Index.InvalidRequestException;

    Boolean retweetPost( RetweetPostDTO retweetPostDTO ) throws Index.InvalidRequestException;

    Boolean reactTweet( ReactionDTO<Post> reactionDTO ) throws Index.InvalidRequestException;

    List<Post> getAccountTweets( String accountId ) throws Index.InvalidRequestException;

    PostConfigurations updatePostConfigurations(String postId, PostConfigurations postConfigurations)
            throws Index.InvalidRequestException;
}
