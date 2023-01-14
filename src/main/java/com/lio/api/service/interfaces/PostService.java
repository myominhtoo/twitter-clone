package com.lio.api.service.interfaces;

import java.util.List;

import com.lio.api.exception.custom.Index;
import com.lio.api.model.dto.ReactionDTO;
import com.lio.api.model.dto.RetweetPostDTO;
import com.lio.api.model.entity.Post;

public interface PostService {
    
    List<Post> getTweets();

    Post createTweet( Post tweet ) throws Index.InvalidRequestException;

    Post editTweet( String tweetId , Post tweet ) throws Index.InvalidRequestException;

    Boolean deleteTweet( String accountId , String tweetId ) throws Index.InvalidRequestException;

    Boolean retweetPost( RetweetPostDTO retweetPostDTO );

    Boolean reactTweet( ReactionDTO<Post> reactionDTO );

    List<Post> getAccountTweets( String accountId ) throws Index.InvalidRequestException;

}
