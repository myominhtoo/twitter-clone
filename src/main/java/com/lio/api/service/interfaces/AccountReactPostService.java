package com.lio.api.service.interfaces;

import com.lio.api.model.entity.AccountReactPost;

public interface AccountReactPostService {

    AccountReactPost createReactionToPost( AccountReactPost accountReactPost );

    AccountReactPost getReactionByAccountAndPost( String accountId , String postId );

    void removeReactionToPost( Integer id );

}
