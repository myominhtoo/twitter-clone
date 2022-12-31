package com.lio.api.service.interfaces;

import java.util.List;

import com.lio.api.model.dto.ReactionDTO;
import com.lio.api.model.dto.RetweetPostDTO;
import com.lio.api.model.entity.Post;

public interface PostService {
    
    List<Post> getTweets();

    Post createTweet( Post tweet );

    Post editTweet( Post tweet );

    Boolean deleteTweet( String tweetId );

    Boolean retweetPost( RetweetPostDTO retweetPostDTO );

    Boolean reactTweet( ReactionDTO<Post> reactionDTO );

}
