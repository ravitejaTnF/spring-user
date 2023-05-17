package com.spring.restapi.user.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.restapi.user.entities.Post;
import com.spring.restapi.user.entities.User;
import com.spring.restapi.user.payload.ApiResponse;
import com.spring.restapi.user.repositories.PostRepository;
import com.spring.restapi.user.repositories.UserRepository;

import jakarta.validation.Valid;

@SuppressWarnings("rawtypes")
@RestController
public class UserJpaResourceController extends RepresentationModel{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired(required = true)
	private UserRepository repository;
	
	@Autowired 
	private PostRepository postRepository;
	
	@GetMapping(path = "/jpa/users")
	public List<User> retrieveAllUsers(){
		return repository.findAll();
	}
	
	@GetMapping(path = "/jpa/users/{id}")
	public ResponseEntity<ApiResponse<String>> retrieveSingleUser(@PathVariable Integer id) {
		Optional<User> user = repository.findById(id);
		if(user.isEmpty()) {
			ApiResponse<String> nodata = new ApiResponse<>();
			nodata.setMessage("User not found with id: "+id);
			nodata.setSuccess(false);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(nodata);
		}
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true,"User found",user));
	}

	@PostMapping(path = "/jpa/users")
	public ResponseEntity<ApiResponse<String>> createNewUser(@Valid @RequestBody User user) {
		repository.save(user);
		ApiResponse<String> resp = new ApiResponse<>();
		resp.setSuccess(true);
		resp.setMessage("User created successfully!");
		return new ResponseEntity<>(resp,HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/jpa/users/{id}")
	public ResponseEntity<ApiResponse<String>> deleteUser(@PathVariable int id) {
		Optional<User> data = repository.findById(id);
		if(!data.isEmpty()) {
			repository.deleteById(id);
			ApiResponse<String> response = new ApiResponse<>();
			response.setSuccess(true);
			response.setMessage("User deleted successfully!");
			return new ResponseEntity<>(response,HttpStatus.OK);
		}
		ApiResponse<String> response = new ApiResponse<>();
		response.setSuccess(false);
		response.setMessage("User not found");
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@PatchMapping(path = "/jpa/users/{id}")
	public ResponseEntity<ApiResponse> updateUserDetails(@PathVariable int id,@RequestBody User user) {
		Optional<User> findById = repository.findById(id);
		if(findById.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,"user not found",null));
		if(user.getId() != null)
			findById.get().setId(user.getId());
		if(user.getName() != null)
			findById.get().setName(user.getName());
		if(user.getBirthDate() != null)
			findById.get().setBirthDate(user.getBirthDate());
		repository.save(findById.get());
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true,"User details modified!",findById.get()));
	}
	
	@GetMapping(path = "/jpa/users/{id}/posts")
	public ResponseEntity<ApiResponse> retrievePosts(@PathVariable int id){
		Optional<User> user = repository.findById(id);
		if(user.get().getPosts().isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(new ApiResponse(false,"No posts found for the user",null));
		}
		return ResponseEntity.status(HttpStatus.OK)
				.body(new ApiResponse(true,"Posts found for user",user.get().getPosts()));
	}
	
	@PostMapping(path = "/jpa/users/{id}/posts")
	public ResponseEntity<ApiResponse<String>> createPostForUser(@PathVariable int id,@Valid @RequestBody Post post) {
		Optional<User> user1 = repository.findById(id);
		if(user1.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,String.format("User not found with id:", id),null));
		post.setUser(user1.get());
		Post savedpost = postRepository.save(post);
		ApiResponse<String> postResponse = new ApiResponse<>();
		postResponse.setSuccess(true);
		postResponse.setMessage("Post created!");
		return new ResponseEntity<>(postResponse,HttpStatus.OK);
	}
	
	@GetMapping(path = "/jpa/users/{userId}/posts/{postId}")
	public ResponseEntity<ApiResponse> getPostOfUserByPostId(@PathVariable int userId,@PathVariable int postId) {
		Optional<User> user = repository.findById(userId);
		Optional<Post> post = postRepository.findById(postId);
		if(user.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,String.format("user not found with id :%d",userId),null));
		if(post.isEmpty() || post.get().getUser().getId() != userId)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false,String.format("post not found for userId:%d with postId:%d",userId,postId),null));
		System.out.println(post.isEmpty());
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(true,"Post found for user",post.get()));
	}
}
