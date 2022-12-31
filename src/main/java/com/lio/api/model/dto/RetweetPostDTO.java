package com.lio.api.model.dto;

import com.lio.api.model.entity.Account;
import com.lio.api.model.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetweetPostDTO {
    
    private Account from;
    private Post post;

}
