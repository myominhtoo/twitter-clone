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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "post_configurations" )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostConfigurations {
    
    @Id
    @GeneratedValue( strategy =  GenerationType.AUTO )
    private Integer id;

    @Column( name = "privacy_status" )
    @NotNull( message = "${field.required}" )
    @NotBlank( message =  "${field.required}" )
    private Integer privacyStatus;

    @Column( name = "retweet_status"  )
    @NotNull( message = "${field.required}" )
    @NotBlank( message =  "${field.required}" )
    private Integer retweetStatus;

    @Column( name = "comment_status" )
    @NotNull( message = "${field.required}" )
    @NotBlank( message =  "${field.required}" )
    private Integer commentStatus;

    @Column( name = "reaction_status" )
    @NotNull( message = "${field.required}" )
    @NotBlank( message =  "${field.required}" )
    private Integer reactionStatus;

    @Column( name = "created_date" )
    private LocalDateTime createdDate;

    @Column( name = "updated_date" )
    private LocalDateTime updatedDate;

    @OneToOne
    @JoinColumn( name = "post_id" )
    private Post post;

}
