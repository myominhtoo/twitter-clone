package com.lio.api.controller.post;

import com.lio.api.controller.ResourceConfig;
import com.lio.api.exception.custom.Index;
import com.lio.api.model.dto.ApiResponse;
import com.lio.api.model.entity.Post;
import com.lio.api.service.interfaces.AccountService;
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

    @Autowired
    public PostController(
            PostService postService ,
            AccountService accountService
    ){
        this.postService = postService;
        this.accountService = accountService;
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


}
