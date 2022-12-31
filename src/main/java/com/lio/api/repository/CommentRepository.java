package com.lio.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lio.api.model.entity.Comment;

@Repository
public interface CommentRepository  extends JpaRepository<Comment,Integer> {
    
}
