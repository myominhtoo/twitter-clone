package com.lio.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lio.api.model.entity.PostConfigurations;

@Repository
public interface PostConfigRepository extends JpaRepository<PostConfigurations,Integer> {
    
}
