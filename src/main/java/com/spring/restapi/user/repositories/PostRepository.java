package com.spring.restapi.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.restapi.user.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer>{

}
