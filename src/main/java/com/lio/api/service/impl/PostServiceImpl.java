package com.lio.api.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.lio.api.exception.custom.Index;
import com.lio.api.model.dto.ReactionDTO;
import com.lio.api.model.dto.RetweetPostDTO;
import com.lio.api.model.entity.*;
import com.lio.api.repository.AccountTweetPostRepository;
import com.lio.api.repository.PostRepository;
import com.lio.api.service.interfaces.AccountReactPostService;
import com.lio.api.service.interfaces.AccountService;
import com.lio.api.service.interfaces.PostConfigService;
import com.lio.api.service.interfaces.PostService;
import com.lio.api.util.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.lio.api.model.constant.Messages.INVALID_REQUEST;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final AccountService accountService;

    private final AccountTweetPostRepository accountTweetPostRepo;

    private final AccountReactPostService accountReactPostService;

    private final PostConfigService postConfigService;

    @Autowired
    public PostServiceImpl(
            PostRepository postRepository,
            AccountService accountService ,
            AccountTweetPostRepository accountTweetPostRepo ,
            PostConfigService postConfigService ,
            AccountReactPostService accountReactPostService
    ){
        this.postRepository = postRepository;
        this.accountService = accountService;
        this.accountTweetPostRepo = accountTweetPostRepo;
        this.postConfigService = postConfigService;
        this.accountReactPostService = accountReactPostService;
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
        Post createdTweet  = this.postRepository.save(tweet);

        return createdTweet;
    }

    @Override
    public Boolean deleteTweet( String accountId , String tweetId)
            throws Index.InvalidRequestException {
        Post savedTweet= this.postRepository.findById( tweetId )
                .orElseThrow(() -> new Index.InvalidRequestException( INVALID_REQUEST ) );

        if( !savedTweet.getCreatedAccount().getId().equals(accountId) || savedTweet.getIsDelete() ){
            throw new Index.InvalidRequestException( INVALID_REQUEST );
        }

        savedTweet.setIsDelete(true);
        return this.postRepository.save(savedTweet) != null;
    }

    @Override
    public Post getTweet(String postId) throws Index.InvalidRequestException {
        Post savedTweet = this.postRepository.findById(postId)
                .orElseThrow(() -> new Index.InvalidRequestException( INVALID_REQUEST ));
        return savedTweet;
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
    public Boolean reactTweet(ReactionDTO<Post> reactionDTO)
            throws Index.InvalidRequestException {
        Account fromAccount = this.accountService
                .getAccount( reactionDTO.getAccount().getId());
        Post targetPost = this.getTweet(reactionDTO.getTarget().getId());

        AccountReactPost accountReactPost = this.accountReactPostService
                .getReactionByAccountAndPost( fromAccount.getId() , targetPost.getId() );
        if( accountReactPost != null ){
            this.accountReactPostService.removeReactionToPost(accountReactPost.getId());
            targetPost.setReactionCount(
                    targetPost.getReactionCount() == null
                    ? 0 : targetPost.getReactionCount() - 1
            );
            return this.postRepository.save(targetPost) != null;
        }
        accountReactPost = new AccountReactPost();
        accountReactPost.setPost(targetPost);
        accountReactPost.setAccount(fromAccount);

        accountReactPost = this.accountReactPostService.createReactionToPost(accountReactPost);
        targetPost.setReactionCount(
                targetPost.getReactionCount() == null
                ? 1 : targetPost.getReactionCount() + 1
        );
        return accountReactPost != null && this.postRepository.save(targetPost) != null;
    }

    @Override
    public Boolean retweetPost(RetweetPostDTO retweetPostDTO)
            throws Index.InvalidRequestException {

        Post targetPost = this.getTweet(retweetPostDTO.getPost().getId());

        if( this.accountService.getAccount(retweetPostDTO.getFrom().getId()) == null  ||
                targetPost == null
          ){
            throw new Index.InvalidRequestException( INVALID_REQUEST );
        }

        AccountTweetPost accountTweetPost = AccountTweetPost.builder()
                .account(retweetPostDTO.getFrom())
                .post(retweetPostDTO.getPost())
                .createdDate(LocalDateTime.now())
                .isDelete(false)
                .build();

        targetPost.setUpdatedDate(LocalDateTime.now());
        targetPost.setTweetCount(
                targetPost.getTweetCount() == null
                        ? 1
                        : targetPost.getTweetCount() + 1
        );

        return ( this.accountTweetPostRepo.save(accountTweetPost) != null &&
                this.postRepository.save(targetPost) != null );
    }

    @Override
    public PostConfigurations updatePostConfigurations( String postId , PostConfigurations postConfigurations)
            throws Index.InvalidRequestException {
        if( !postId.equals(postConfigurations.getPost().getId())){
            throw new Index.InvalidRequestException( INVALID_REQUEST );
        }
        return this.postConfigService.updatePostConfigurations( postId , postConfigurations );
    }
}
