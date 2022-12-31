package com.lio.api.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "posts" )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    
    @Id
    private String id;
    
    @Column( name = "title" )
    @NotNull( message = "${field.required}" )
    @NotBlank( message =  "${field.required}" )
    private String title;

    @Column( name = "description" )
    private String description;

    @Column( name = "created_date" )
    private LocalDateTime createdDate;

    @Column( name = "updated_date" )
    private LocalDateTime updatedDate;

    @Transient
    private MultipartFile postImage;

    @Column( name = "post_image_uri" )
    private String postImageUri;

    @ManyToOne
    @JoinColumn( name = "account_id" )
    private Account createdAccount;

}
