package com.lio.api.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table( name = "account_follow_account" )
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountFollowAccount {

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private Integer id;

    @OneToOne
    @JoinColumn( name = "from_account_id" )
    private Account fromAccount;

    @OneToOne
    @JoinColumn( name = "to_account_id" )
    private Account toAccount;

    @Column( name = "created_date" )
    @JsonFormat( shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd hh:mm:ss" )
    private LocalDateTime createdDate;

}
