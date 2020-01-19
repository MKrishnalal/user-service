package com.ncs.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ncs.entity.User;


public interface UserDAO extends CrudRepository<User,Long>{

	public User findByUserid(String userid);
	public Optional<User> findById(Long id);
}
