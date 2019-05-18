package com.luban.service;

import java.util.List;

import com.luban.entity.User;

public interface UserService {

	public User findById(Integer userId);
	
	public List<User> findAll();
}
