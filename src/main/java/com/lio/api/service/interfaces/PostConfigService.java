package com.lio.api.service.interfaces;

import com.lio.api.model.entity.Post;
import com.lio.api.model.entity.PostConfigurations;

public interface PostConfigService {

    PostConfigurations configurePost( PostConfigurations postConfigurations );

    PostConfigurations createDefaultPostConfiguration(Post post );

}
