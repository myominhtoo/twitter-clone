package com.lio.api.service.impl;

import com.lio.api.model.entity.Post;
import com.lio.api.model.entity.PostConfigurations;
import com.lio.api.repository.PostConfigRepository;
import com.lio.api.service.interfaces.PostConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("postConfigService")
public class PostConfigServiceImpl implements PostConfigService {

    private final PostConfigRepository postConfigRepository;

    @Autowired
    public PostConfigServiceImpl( PostConfigRepository postConfigRepository ){
        this.postConfigRepository  = postConfigRepository;
    }

    @Override
    public PostConfigurations configurePost(PostConfigurations postConfigurations) {
        return null;
    }

    /*
     currently only for temporary
     */
    @Override
    public PostConfigurations createDefaultPostConfiguration(Post post) {
        PostConfigurations postConfigurations = PostConfigurations.builder()
                .post(post)
                .commentStatus(0)
                .reactionStatus(0)
                .createdDate(LocalDateTime.now())
                .retweetStatus(0)
                .privacyStatus(0)
                .build();
        return this.postConfigRepository.save(postConfigurations);
    }
}
