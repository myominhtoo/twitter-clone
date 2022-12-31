package com.lio.api.model.dto;

import com.lio.api.model.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountFollowDTO {
    
    private Account from;
    private Account to;

}
