package com.lio.api.controller.comment;

import com.lio.api.controller.ResourceConfig;
import com.lio.api.exception.custom.Index;
import com.lio.api.model.dto.ApiResponse;
import com.lio.api.model.dto.ReactionDTO;
import com.lio.api.model.entity.Comment;
import com.lio.api.service.interfaces.CommentService;
import com.lio.api.util.AppUtil;
import com.lio.api.util.CustomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.lio.api.model.constant.Messages.*;

@RestController
@RequestMapping( value = "${api.current.version}")
public class CommentController extends ResourceConfig {

    private final CommentService commentService;

    @Autowired
    public CommentController( CommentService commentService ){
        this.commentService = commentService;
    }

    /*
     for creating comment in post
      won't be able to create comment without related post
      route => /api/v1/comments => (POST)->body(comment)
     */
    @PostMapping( value = "${createComment}")
    public ResponseEntity<ApiResponse<Object>> postCreateComment(
            @Valid @RequestBody Comment comment ,
            BindingResult bindingResult
    ) throws Index.InvalidRequestException {
        if( bindingResult.hasErrors() ){
            return CustomResponse.getErrorResponse(
                    null ,
                    INVALID_REQUEST ,
                    AppUtil.getErrorsMapFromBindingResults(bindingResult)
            );
        }

        Comment createdComment = this.commentService.createComment(comment);
        return CustomResponse.getResponse(
                createdComment ,
                SUCCESS_DONE
        );
    }

    /*
      for editing comment
      won't be able to edit without any comment related to post
      route => /api/v1/comments/{commentId} => (PUT)->body(comment)->path(commentId)
     */
    @PutMapping( value = "${editComment}" )
    public ResponseEntity<ApiResponse<Object>> putEditComment(
           @Valid @RequestBody Comment comment ,
           BindingResult bindingResult
    ) throws Index.InvalidRequestException {
        if( bindingResult.hasErrors() ){
            return CustomResponse.getErrorResponse(
                    null,
                    INVALID_REQUEST ,
                    AppUtil.getErrorsMapFromBindingResults(bindingResult)
            );
        }
        Comment editedComment = this.commentService.editComment(comment);
        return CustomResponse.getResponse( editedComment , SUCCESS_DONE );
    }

    /*
     for getting comments of a tweet
     route => /api/v1/tweets/{tweetId}/comments
     */
    @GetMapping( value = "${getTweetComments}" )
    public ResponseEntity<ApiResponse<Object>> getTweetComments(
            @PathVariable( value = "tweetId" , required = true ) String tweetId
    ){
        return CustomResponse.getResponse(
          this.commentService.getTweetComments( tweetId ),
          REQUEST_SUCCESS
        );
    }

    /*
     for deleting comment
     route => /api/v1/comments/{commentId}
     */
    @DeleteMapping( value ="${deleteComment}")
    public ResponseEntity<ApiResponse<Object>> deleteComment(
            @PathVariable( value = "commentId" ) Integer commentId
    ){
        Boolean deleteStatus = this.commentService.deleteComment(commentId);
        return (
                 deleteStatus
                 ? CustomResponse.getResponse( null , SUCCESS_DONE )
                         : CustomResponse.getErrorResponse( null ,REQUEST_FAILED , null )
                );
    }

    /*
     for toggle reacting comment
     route => /api/v1/react-comment => (POST)->body(reactionDTO)
     */
    @PostMapping( value = "${reactComment}" )
    public ResponseEntity<ApiResponse<Object>> postReactComment(
            @Valid @RequestBody ReactionDTO<Comment> reactionDTO ,
            BindingResult bindingResult
    ) throws Index.InvalidRequestException {
        if( bindingResult.hasErrors() ){
            return CustomResponse.getErrorResponse(
                    null ,
                    INVALID_REQUEST ,
                    AppUtil.getErrorsMapFromBindingResults(bindingResult)
            );
        }

        Boolean deleteStatus = this.commentService.reactComment(reactionDTO);

        return (
                deleteStatus
                ? CustomResponse.getResponse( null , SUCCESS_DONE )
                        : CustomResponse.getErrorResponse( null , REQUEST_FAILED , null )
        );
    }

}