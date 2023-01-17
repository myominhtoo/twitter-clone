package com.lio.api.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.lio.api.model.constant.Messages.REQUIRED_FIELD;

@Entity
@Table( name = "post_configurations" )
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PostConfigurations {

    @Id
    @GeneratedValue( strategy =  GenerationType.IDENTITY )
    private Integer id;

    @Column( name = "privacy_status" )
    @NotNull( message = REQUIRED_FIELD )
    private Integer privacyStatus;

    @Column( name = "comment_status" )
    @NotNull( message = REQUIRED_FIELD )
    private Integer commentStatus;

    @Column( name = "reaction_status" )
    @NotNull( message = REQUIRED_FIELD )
    private Integer reactionStatus;

    @Column( name = "tweet_status" )
    @NotNull( message = REQUIRED_FIELD )
    private Integer tweetStatus;

    @Column( name = "created_date" )
    @JsonFormat( shape = JsonFormat.Shape.STRING ,pattern =  "yyyy-MM-dd HH:mm:ss" )
    private LocalDateTime createdDate;

    @Column( name = "updated_date" )
    private LocalDateTime updatedDate;

    @OneToOne
    @JoinColumn( name = "post_id" )
    private Post post;

}
