package com.lio.api.model.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table( name = "account_react_post" )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountReactPost {
    
    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Integer id;

    @OneToOne
    @JoinColumn( name = "account_id" )
    private Account account;

    @OneToOne
    @JoinColumn( name = "post_id" )
    private Post post;

    @Column( name = "created_date" )
    private LocalDateTime createdDate;

}
