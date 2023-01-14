package com.lio.api.model.dto;

import com.lio.api.model.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import static com.lio.api.model.constant.Messages.REQUIRED_FIELD;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReactionDTO<T> {

    @NotNull( message =  REQUIRED_FIELD )
    private Account account;
    
    /*
     * T is Comment or Post
     */
    @NotNull( message =  REQUIRED_FIELD )
    private T target;

}
