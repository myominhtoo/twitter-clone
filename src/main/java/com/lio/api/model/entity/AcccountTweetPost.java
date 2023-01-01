package com.lio.api.model.entity;

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
@Table( name = "account_tweet_posts" )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AcccountTweetPost {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Integer id;

    @OneToOne
    @JoinColumn( name = "account_id" )
    private Account account;

    @OneToOne
    @JoinColumn( name = "post_id" )
    private Post post;

}
