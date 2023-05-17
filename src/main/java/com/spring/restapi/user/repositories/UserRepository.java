package com.spring.restapi.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.restapi.user.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{

}
