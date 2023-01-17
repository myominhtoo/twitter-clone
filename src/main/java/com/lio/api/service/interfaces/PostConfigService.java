package com.lio.api.service.interfaces;

import com.lio.api.exception.custom.Index;
import com.lio.api.model.entity.Post;
import com.lio.api.model.entity.PostConfigurations;

public interface PostConfigService {

    PostConfigurations createDefaultPostConfigurations( Post post );

    PostConfigurations updatePostConfigurations( String postId , PostConfigurations postConfigurations ) throws Index.InvalidRequestException;

    PostConfigurations getPostConfigurations( String tweetId ) throws Index.InvalidRequestException;

}
