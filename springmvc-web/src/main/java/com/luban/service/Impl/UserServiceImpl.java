package com.luban.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luban.entity.User;
import com.luban.mapper.UserMapper;
import com.luban.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	
	@Override
	public User findById(Integer userId) {
		// TODO Auto-generated method stub
		User user = userMapper.findById(userId);
		return user;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userMapper.findAll();
	}
}
