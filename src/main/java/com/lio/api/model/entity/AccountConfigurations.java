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
@Table( name = "account_configurations" )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountConfigurations {
    
    @Id
    @GeneratedValue( strategy =  GenerationType.AUTO )
    private Integer id;

    @Column( name = "is_mute_ringtone" , nullable = true )
    private Boolean isMuteRingtone;

    @Column( name = "is_lock_profile" , nullable =  true )
    private Boolean isLockProfile;

    @Column( name = "allowed_viewers" , nullable = true )
    private Integer allowedViewers;

    @Column( name = "show_followers" , nullable = true )
    private Boolean showFollowers;

    @Column( name = "show_followings" , nullable = true )
    private Boolean showFollowings;

    @Column( name = "show_notification" , nullable = true )
    private Boolean showNotification;

    @Column( name = "created_date" )
    private LocalDateTime createdDate;

    @Column( name = "updated_date" )
    private LocalDateTime updatedDate;
    
    @OneToOne
    @JoinColumn( name = "account_id" )
    private Account account;

}
