package com.lio.api.service.impl;

import com.lio.api.exception.custom.Index;
import com.lio.api.model.entity.Post;
import com.lio.api.model.entity.PostConfigurations;
import com.lio.api.repository.PostConfigurationsRepository;
import com.lio.api.service.interfaces.PostConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.lio.api.model.constant.Messages.INVALID_REQUEST;

@Service
public class PostConfigServiceImpl implements PostConfigService {

    private PostConfigurationsRepository postConfigRepo;

    @Autowired
    public PostConfigServiceImpl( PostConfigurationsRepository postConfigRepo ){
        this.postConfigRepo = postConfigRepo;
    }

    @Override
    public PostConfigurations createDefaultPostConfigurations(Post post) {
        PostConfigurations postConfigurations = PostConfigurations.builder()
                .post(post)
                .commentStatus(0)
                .privacyStatus(0)
                .reactionStatus(0)
                .tweetStatus(0)
                .createdDate(LocalDateTime.now())
                .build();
        return this.postConfigRepo.save(postConfigurations);
    }

    @Override
    public PostConfigurations updatePostConfigurations( String postId , PostConfigurations postConfigurations) throws Index.InvalidRequestException {

        if( postConfigurations.getId() == null ||
                !postId.equals(postConfigurations.getPost().getId()) ){
            throw new Index.InvalidRequestException( INVALID_REQUEST );
        }

        PostConfigurations savedPostConfigs = this.postConfigRepo.findById(postConfigurations.getId())
                .orElseThrow( () -> new Index.InvalidRequestException( INVALID_REQUEST ));
        postConfigurations.setUpdatedDate(LocalDateTime.now());
        return this.postConfigRepo.save( postConfigurations );
    }

    @Override
    public PostConfigurations getPostConfigurations( String tweetId )
            throws Index.InvalidRequestException {
        PostConfigurations postConfigurations = this.postConfigRepo
                .findByPostId(tweetId);
        if( postConfigurations == null ){
            throw new Index.InvalidRequestException( INVALID_REQUEST );
        }
        return postConfigurations;
    }

}
