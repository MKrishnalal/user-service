package com.ncs.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ncs.service.UserService;
import com.ncs.dao.UserDAO;
import com.ncs.entity.SuperUser;
import com.ncs.entity.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDAO userRepository;
	
	@Override
	public List<User> getUsers() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(users::add);
		return users;
	}
	
	@Override
	public List<SuperUser> getUsersWithRole() {
		List<SuperUser> users = new ArrayList<>();
		userRepository.findAll().forEach(user->{
			SuperUser suser = new SuperUser("Super User", user);
			users.add(suser);
		});
		return users;
	}

	@Override
	public User modifyUser(User user) {
		User userOld = userRepository.findByUserid(user.getUserid());
		if(userOld == null) {
			return null;
		}
		User userUpdated = userRepository.save(user);
		return userUpdated;
		
	}

	@Override
	public String addUser(User user) {
		String userId = genearteUserId(user.getFname(), user.getLname());
		user.setUserid(userId);
		userRepository.save(user);
		System.out.println("User id "+userId);
		return userId;
	}

	private String genearteUserId(String fName,String lName) {
		String userId = fName.substring(0,2) + lName.substring(0,2);
		return userId;
	}

	@Override
	public User getUser(Long id) {
		Optional<User> userOpt = userRepository.findById(id);
		User user = userOpt.orElse(createDummyUser());	
		return user;
	}
	
	
	private User createDummyUser() {
		return new User();
	}

	@Override
	public String remove(Long id) {
		userRepository.deleteById(id);
		return "User Removed!!!";
	}
	
	
	
}
