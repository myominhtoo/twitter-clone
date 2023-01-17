package com.lio.api.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import com.lio.api.exception.custom.Index;
import com.lio.api.model.dto.ReactionDTO;
import com.lio.api.model.entity.Account;
import com.lio.api.model.entity.AccountReactComment;
import com.lio.api.model.entity.Comment;
import com.lio.api.model.entity.Post;
import com.lio.api.repository.CommentRepository;
import com.lio.api.service.interfaces.AccountReactCommentService;
import com.lio.api.service.interfaces.AccountService;
import com.lio.api.service.interfaces.CommentService;
import com.lio.api.service.interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.lio.api.model.constant.Messages.INVALID_REQUEST;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AccountService accountService;

    private final PostService postService;

    private final AccountReactCommentService accountReactCommentService;

    @Autowired
    public CommentServiceImpl(
            CommentRepository commentRepository ,
            AccountService accountService ,
            PostService postService ,
            AccountReactCommentService accountReactCommentService
    ){
        this.commentRepository = commentRepository;
        this.accountService = accountService;
        this.postService = postService;
        this.accountReactCommentService = accountReactCommentService;
    }

    @Override
    public Comment createComment(Comment comment) throws Index.InvalidRequestException {
        Account commentedAccount = this.accountService
                .getAccount(comment.getCommentedAccount().getId());
        Post targetPost = this.postService.getTweet(comment.getPost().getId());

        if( commentedAccount ==  null || targetPost == null ){
            throw new Index.InvalidRequestException( INVALID_REQUEST );
        }
        comment.setCommentedAccount(commentedAccount);
        comment.setPost(targetPost);
        comment.setCreatedDate(LocalDateTime.now());
        return  this.commentRepository.save(comment);
    }

    @Override
    public Comment getComment(Integer commentId)
            throws Index.InvalidRequestException {
        return this.commentRepository.findById(commentId)
                .orElseThrow(() -> new Index.InvalidRequestException( INVALID_REQUEST ));
    }

    @Override
    public Boolean deleteComment(Integer commentId) {
        return null;
    }

    @Override
    public Comment editComment(Comment comment)
            throws Index.InvalidRequestException {
        Comment targetComment = this.getComment(comment.getId());

        if( targetComment == null ||
                this.postService.getTweet(comment.getPost().getId()) == null ||
                this.accountService.getAccount(comment.getCommentedAccount().getId()) == null ||
                !targetComment.getCommentedAccount().getId()
                        .equals(comment.getCommentedAccount().getId())
        ){
            throw new Index.InvalidRequestException( INVALID_REQUEST );
        }
        comment.setUpdatedDate(LocalDateTime.now());
        return this.commentRepository.save(comment);
    }

    @Override
    public List<Comment> getTweetComments(String tweetId) {
       return this.commentRepository.findByPostId(tweetId);
    }

    @Override
    public Boolean reactComment(ReactionDTO<Comment> reactionDTO)
            throws Index.InvalidRequestException {
        Account account = this.accountService.getAccount( reactionDTO.getAccount().getId());
        Comment targetComment = this.getComment(reactionDTO.getTarget().getId());
        if( account == null || targetComment == null ){
            throw new Index.InvalidRequestException( INVALID_REQUEST );
        }


        AccountReactComment accountReactComment = this.accountReactCommentService
                .getAccountReactCommentByAccountIdAndCommentId( account.getId() , targetComment.getId() );
        if( accountReactComment != null ){
            targetComment.setReactionCount(
                    targetComment.getReactionCount() == null
                    ? 0 : targetComment.getReactionCount() - 1
            );
            this.commentRepository.save( targetComment );
            return this.accountReactCommentService
                    .deleteAccountReactComment(accountReactComment.getId());
        }

        targetComment.setReactionCount(
                targetComment.getReactionCount() == null
                ? 1 : targetComment.getReactionCount()  + 1
        );
        /*
         updating reaction count
         */
        this.commentRepository.save(targetComment);

        accountReactComment = this.accountReactCommentService.createAccountComment(reactionDTO);
        return accountReactComment != null;

    }
    
}
