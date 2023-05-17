package com.spring.restapi.user.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.spring.restapi.user.entities.User;
import com.spring.restapi.user.services.UserDaoService;
import com.spring.zexception.handler.UserNotFoundException;

import jakarta.validation.Valid;

@RestController
public class UserResourceController extends RepresentationModel{
	
	@Autowired
	private UserDaoService userService;
	
	@GetMapping(path = "/users")
	public List<User> retrieveAllUsers(){
		return userService.findAll();
	}
	
	@GetMapping(path = "/users/{id}")
	public EntityModel retrieveSingleUser(@PathVariable Integer id) {
		User user = userService.findOne(id);
		if(user == null)
			throw new UserNotFoundException("id : "+id);
		
		EntityModel<User> entityModel = EntityModel.of(user);
		
		WebMvcLinkBuilder link = linkTo(methodOn(UserResourceController.class).retrieveAllUsers());
		entityModel.add(link.withRel("all-users"));
		return entityModel;
	}

	@PostMapping(path = "/users")
	public ResponseEntity<User> createNewUser(@Valid @RequestBody User user) {
		User newUser = userService.saveUser(user);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()
									.path("/{id}")
									.buildAndExpand(newUser.getId())
									.toUri();
		return ResponseEntity.created(location).body(newUser);
	}
	
	@DeleteMapping(path = "/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userService.deleteById(id);
	}
}
