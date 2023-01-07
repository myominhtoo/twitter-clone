package com.lio.api.model.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.lio.api.model.constant.Messages.REQUIRED_FIELED;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table( name = "accounts" )
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    
    @Id
    private String id;

    @Column( name = "first_name" )
    @NotNull( message = REQUIRED_FIELED )
    @NotBlank( message =  REQUIRED_FIELED )
    private String firstName;

    @Column( name = "last_name" )
    @NotNull( message = REQUIRED_FIELED )
    @NotBlank( message =  REQUIRED_FIELED )
    private String lastName;

    @Transient
    private String fullName;

    @Column( name = "email" )
    @NotNull( message = REQUIRED_FIELED )
    @NotBlank( message =  REQUIRED_FIELED )
    private String email;

    @Column( name = "password" )
    @NotNull( message = REQUIRED_FIELED )
    @NotBlank( message =  REQUIRED_FIELED )
    private String password;

    @Transient
    private String confirmPassword;

    @Column( name = "phone" )
    private String phone;

    @Column( name = "dob" )
    private LocalDate dob;

    @Column( name = "age" )
    private Integer age;

    @Column( name = "bios" )
    private String bios;  

    @Column( name = "device_id" )
    @NotNull( message = REQUIRED_FIELED )
    @NotBlank( message =  REQUIRED_FIELED )
    private String deviceId;

    @Column( name = "created_date" )
    private LocalDateTime createdDate;

    @Column( name = "updated_date" )
    private LocalDateTime updatedDate;

    @Column( name = "last_logged_in_date")
    private LocalDateTime lastLoggedInDate;

    @Column( name = "last_logged_in_device_id" )
    private LocalDateTime lastLoggedInDeviceId;

    @Column( name = "verification_code" )
    private Integer verificationCode;

    @Column( name = "has_verified_code" )
    private Boolean hasVerifiedCode;

}
