package com.lio.api.model.dto;

import com.lio.api.model.entity.Account;
import com.lio.api.model.entity.Post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import static com.lio.api.model.constant.Messages.REQUIRED_FIELD;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RetweetPostDTO {

    @NotNull( message = REQUIRED_FIELD )
    private Account from;

    @NotNull( message = REQUIRED_FIELD )
    private Post post;

}
