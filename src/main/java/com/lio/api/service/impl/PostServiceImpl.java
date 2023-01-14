package com.lio.api.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.lio.api.exception.custom.Index;
import com.lio.api.model.dto.ReactionDTO;
import com.lio.api.model.dto.RetweetPostDTO;
import com.lio.api.model.entity.Account;
import com.lio.api.model.entity.Post;
import com.lio.api.repository.PostRepository;
import com.lio.api.service.interfaces.AccountService;
import com.lio.api.service.interfaces.PostService;
import com.lio.api.util.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import static com.lio.api.model.constant.Messages.INVALID_REQUEST;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final AccountService accountService;

    @Autowired
    public PostServiceImpl(
            PostRepository postRepository,
            AccountService accountService
    ){
        this.postRepository = postRepository;
        this.accountService = accountService;
    }

    @Override
    public Post createTweet(Post tweet)
            throws Index.InvalidRequestException {
        if( tweet.getCreatedAccount() == null ||
                accountService.getAccount(tweet.getCreatedAccount().getId()) == null
        ){
            throw new Index.InvalidRequestException( INVALID_REQUEST );
        }
        tweet.setCreatedDate(LocalDateTime.now());
        tweet.setUpdatedDate(null);
        tweet.setIsDelete(false);
        tweet.setId(Generator.generateId("post"));
        return this.postRepository.save(tweet);
    }

    @Override
    public Boolean deleteTweet( String accountId , String tweetId)
            throws Index.InvalidRequestException {
        Post savedTweet= this.postRepository.findById( tweetId )
                .orElseThrow(() -> new Index.InvalidRequestException( INVALID_REQUEST ) );

        if( !savedTweet.getCreatedAccount().getId().equals(accountId) ){
            throw new Index.InvalidRequestException( INVALID_REQUEST );
        }

        savedTweet.setIsDelete(true);
        return this.postRepository.save(savedTweet) != null;
    }

    @Override
    public Post editTweet(String tweetId, Post tweet) throws Index.InvalidRequestException {
        Post savedPost = this.postRepository.findById( tweet.getId() )
                .orElseThrow( () -> new Index.InvalidRequestException(INVALID_REQUEST));

        if( !tweetId.equals(tweet.getId()) ||
                !tweet.getCreatedAccount().getId().equals(savedPost.getCreatedAccount().getId())
        ){
            throw new Index.InvalidRequestException(INVALID_REQUEST);
        }

        tweet.setId(savedPost.getId());
        tweet.setUpdatedDate(LocalDateTime.now());
        tweet.setIsDelete(false);
        return this.postRepository.save(tweet);
    }

    @Override
    public List<Post> getAccountTweets(String accountId)
            throws Index.InvalidRequestException {
        Account targetAccount = this.accountService.getAccount( accountId );
        return this.postRepository.findByCreatedAccountId( targetAccount.getId() );
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
