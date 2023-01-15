package com.lio.api.model.entity;

import java.time.LocalDateTime;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.lio.api.model.constant.Messages.REQUIRED_FIELD;

@Entity
@Table( name = "comments" )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    
    @Id
    @GeneratedValue( strategy =  GenerationType.AUTO )
    private Integer id;

    @NotNull( message =  REQUIRED_FIELD )
    @NotEmpty( message =  REQUIRED_FIELD )
    @Column( name = "content" )
    private String content;

    @Column( name = "created_date" )
    private LocalDateTime createdDate;

    @Column( name = "updated_date" )
    private LocalDateTime updatedDate;

    @Column( name = "reaction_count"  )
    private Integer reactionCount;

    @NotNull( message =  REQUIRED_FIELD )
    @OneToOne
    @JoinColumn( name = "commented_account_id" )
    private Account commentedAccount;

    @OneToOne
    @JoinColumn( name = "parent_comment_id" )
    private Comment parentComment;

    @NotNull( message = REQUIRED_FIELD )
    @ManyToOne( fetch =  FetchType.LAZY )
    @JoinColumn( name = "post_id" )
    private Post post;

}
