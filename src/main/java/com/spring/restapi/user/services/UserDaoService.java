package com.spring.restapi.user.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.restapi.user.entities.User;

@Component
public class UserDaoService {
	
	private static List<User> users = new ArrayList<>();
	private static int usersCount = 0;
	static {
		users.add(new User(++usersCount,"chris evans",LocalDate.now().minusYears(30)));
		users.add(new User(++usersCount,"Adam",LocalDate.now().minusYears(20)));
		users.add(new User(++usersCount,"Robert",LocalDate.now().minusYears(40)));
	}
	//returns all the users data
	public List<User> findAll(){
		return users;
	}
	
	//returns a single user
	public User findOne(Integer Id) {
		Predicate<? super User> predicate = user -> user.getId().equals(Id);
		//return users.get(Id-1);
		return users.stream().filter(predicate).findAny().orElse(null);
	}
	
	//saves a new user
	public User saveUser(User user) {
		user.setId(++usersCount);
		users.add(user);
		return user;
	}
	
	//delete a user
	public void deleteById(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		users.remove(predicate);
	}
}
