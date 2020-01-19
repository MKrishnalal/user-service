package com.ncs.service;

import java.util.List;

import com.ncs.entity.SuperUser;
import com.ncs.entity.User;

public interface UserService {
	
	public List<User> getUsers();
	public User getUser(Long id);
	public User modifyUser(User user);
	public String addUser(User user);
	public String remove(Long id);
	List<SuperUser> getUsersWithRole();

}
