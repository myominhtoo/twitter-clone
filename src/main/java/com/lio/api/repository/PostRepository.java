package com.lio.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lio.api.model.entity.Post;

import java.util.List;


@Repository("postRepository")
public interface PostRepository extends JpaRepository<Post,String> {

    List<Post> findByCreatedAccountId( String accountId );

}
