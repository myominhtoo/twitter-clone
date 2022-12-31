package com.lio.api.model.dto;

import com.lio.api.model.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReactionDTO<T> {
    
    private Account account;
    
    /*
     * T is Comment or Post
     */
    private T target;

}
