package com.lio.api.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "comments" )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    
    @Id
    @GeneratedValue( strategy =  GenerationType.AUTO )
    private Integer id;

    @Column( name = "content" )
    private String content;

    @Column( name = "created_date" )
    private LocalDateTime createdDate;

    @Column( name = "updated_date" )
    private LocalDateTime updatedDate;

    @OneToOne
    @JoinColumn( name = "commented_account_id" )
    private Account commentedAccount;

    @OneToOne
    @JoinColumn( name = "parent_comment_id" )
    private Comment parentComment;

}
