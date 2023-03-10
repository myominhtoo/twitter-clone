package com.lio.api.controller.post;

import com.lio.api.controller.ResourceConfig;
import com.lio.api.exception.custom.Index;
import com.lio.api.model.dto.ApiResponse;
import com.lio.api.model.dto.ReactionDTO;
import com.lio.api.model.dto.RetweetPostDTO;
import com.lio.api.model.entity.Post;
import com.lio.api.model.entity.PostConfigurations;
import com.lio.api.service.interfaces.AccountService;
import com.lio.api.service.interfaces.PostConfigService;
import com.lio.api.service.interfaces.PostService;
import com.lio.api.util.AppUtil;
import com.lio.api.util.CustomResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.lio.api.model.constant.Messages.*;

@Slf4j
@RestController
@RequestMapping( value = "${api.current.version}" )
public class PostController extends ResourceConfig {

    private final PostService postService;

    private final AccountService accountService;

    private final PostConfigService postConfigService;

    @Autowired
    public PostController(
            PostService postService ,
            AccountService accountService ,
            PostConfigService postConfigService
    ){
        this.postService = postService;
        this.accountService = accountService;
        this.postConfigService = postConfigService;
    }

    /*
     for creating tweet
     route => /api/v1/tweets => (POST)=>body(post)
     */
    @PostMapping( value = "${createTweet}" )
    public ResponseEntity<ApiResponse<Object>> postCreateAccount(
            @Valid @RequestBody Post post ,
            BindingResult bindingResult
    ) throws Index.InvalidRequestException {
        if( bindingResult.hasErrors() ){
            return CustomResponse.getErrorResponse(
                    null,
                    INVALID_REQUEST,
                    AppUtil.getErrorsMapFromBindingResults(bindingResult)
            );
        }
        Post createdTweet = this.postService.createTweet(post);
        this.postConfigService.createDefaultPostConfigurations(createdTweet);
        return ( createdTweet == null
                 ? CustomResponse.getErrorResponse( null , INVALID_REQUEST , null )
                 : CustomResponse.getResponse( createdTweet ,SUCCESS_DONE )
                );
    }

    /*
     for editing created post
     route => /api/v1/tweets/{tweetId} => (PUT)->body(tweet)
     */
    @PutMapping( value = "${editTweet}" )
    public ResponseEntity<ApiResponse<Object>> putEditTweet(
            @PathVariable( value = "tweetId" , required = true ) String tweetId ,
            @Valid @RequestBody Post post ,
            BindingResult bindingResult
    ) throws Index.InvalidRequestException {
        if( bindingResult.hasErrors() ){
            return CustomResponse.getErrorResponse(
              null,
                    INVALID_REQUEST,
                    AppUtil.getErrorsMapFromBindingResults(bindingResult)
            );
        }
        Post updatedPost = this.postService.editTweet( tweetId , post );
        return CustomResponse.getResponse( updatedPost , SUCCESS_DONE );
    }

    /*
      for temporary deleting tweet of account
     route => /accounts/{accountId}/tweets/{tweetId} => (DELETE)->path(accountId)->path(tweetId)
     */
    @DeleteMapping( value = "${deleteAccountTweet}" )
    public ResponseEntity<ApiResponse<Object>> deleteAccountTweet(
            @PathVariable( value = "accountId" , required = true ) String accountId ,
            @PathVariable( value = "tweetId" , required = true ) String tweetId
    ) throws Index.InvalidRequestException {
        Boolean deleteStatus = this.postService.deleteTweet( accountId , tweetId );

        return ( deleteStatus
                 ? CustomResponse.getResponse( null , SUCCESS_DONE )
                 : CustomResponse.getErrorResponse( null , REQUEST_FAILED , null )
                );
    }


    /*
     for getting tweets for account
     route => /api/v1/accounts/{accountId}/tweets => (GET)->param(accountId0
     */
    @GetMapping( value = "${getAccountTweets}" )
    public ResponseEntity<ApiResponse<Object>> getTweetsForAccount(
            @PathVariable( value = "accountId" , required = true ) String accountId
    ) throws Index.InvalidRequestException {
        return CustomResponse.getResponse(
                this.postService.getAccountTweets(accountId) ,
                REQUEST_SUCCESS
        );
    }

    /*
     for retweeting posts
     route => /api/v1/retweet => (POST)->body(retweetPostDTO)
     */
    @PostMapping( value = "${retweetPost}" )
    public ResponseEntity<ApiResponse<Object>> retweetPost(
           @Valid @RequestBody RetweetPostDTO retweetPostDTO ,
           BindingResult bindingResult
    ) throws Index.InvalidRequestException {
        if( bindingResult.hasErrors() ){
            return CustomResponse.getErrorResponse(
                    null ,
                    REQUEST_FAILED ,
                    AppUtil.getErrorsMapFromBindingResults(bindingResult)
            );
        }

        Boolean tweetStatus = this.postService.retweetPost(retweetPostDTO);

        return ( tweetStatus
                ? CustomResponse.getResponse( null , SUCCESS_DONE )
                : CustomResponse.getErrorResponse( null , REQUEST_FAILED , null )
        );
    }


    /*
     for configure tweet setting
     route => /tweets/{tweetId}/configure
     */
    @PutMapping( value = "${configureTweet}" )
    public ResponseEntity<ApiResponse<Object>> configurePost(
            @PathVariable( value = "tweetId" , required = true ) String tweetId ,
            @Valid @RequestBody PostConfigurations postConfigurations ,
            BindingResult bindingResult
    ) throws Index.InvalidRequestException {
        if( bindingResult.hasErrors() ){
            return CustomResponse.getErrorResponse(
                    null,
                    INVALID_REQUEST ,
                    AppUtil.getErrorsMapFromBindingResults(bindingResult)
            );
        }
        postConfigurations = this.postConfigService.updatePostConfigurations( tweetId , postConfigurations );
        return CustomResponse.getResponse( postConfigurations , SUCCESS_DONE );
    }


    /*
     for toggling reaction
     route => /api/v1/react-tweet => (POST)->body(reactionDTO)
     */
    @PostMapping( value = "${toggleReactionTweet}")
    public ResponseEntity<ApiResponse<Object>> toggleReactionTweet(
            @Valid @RequestBody ReactionDTO<Post> reactionDTO ,
            BindingResult bindingResult
    ) throws Index.InvalidRequestException {
        if( bindingResult.hasErrors() ){
            return CustomResponse.getErrorResponse(
                    null ,
                    INVALID_REQUEST ,
                    AppUtil.getErrorsMapFromBindingResults( bindingResult )
            );
        }

        Boolean reactionStatus = this.postService.reactTweet(reactionDTO);

        return ( reactionStatus
                ? CustomResponse.getResponse( null , SUCCESS_DONE )
                : CustomResponse.getErrorResponse( null , REQUEST_FAILED , null )
        );
    }

    /*
     for getting tweet configurations
     */
    @GetMapping( value = "${tweetConfigurations}" )
    public ResponseEntity<ApiResponse<Object>> getTweetConfigurations(
            @PathVariable( value = "tweetId" , required = true ) String tweetId
    ) throws Index.InvalidRequestException {
        PostConfigurations postConfigurations = this.postConfigService.getPostConfigurations(tweetId);

        return CustomResponse.getResponse(
                postConfigurations ,
                REQUEST_SUCCESS
        );
    }

}




