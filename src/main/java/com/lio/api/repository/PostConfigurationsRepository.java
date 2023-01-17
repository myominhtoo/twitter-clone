package com.lio.api.repository;

import com.lio.api.model.entity.PostConfigurations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostConfigurationsRepository extends JpaRepository<PostConfigurations,Integer> {

    PostConfigurations findByPostId( String postId );

}
