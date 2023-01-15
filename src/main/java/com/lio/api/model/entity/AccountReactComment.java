package com.lio.api.model.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table( name = "account_react_comments" )
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountReactComment {

    @Id
    @GeneratedValue( strategy =  GenerationType.AUTO )
    private Integer id;

    @OneToOne
    @JoinColumn( name = "account_id" )
    private Account account;

    @OneToOne
    @JoinColumn( name = "comment_id" )
    private Comment comment;

    @Column( name = "created_date" )
    private LocalDateTime createdDate;

}
