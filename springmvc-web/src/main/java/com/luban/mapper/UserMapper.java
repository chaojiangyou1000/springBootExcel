package com.luban.mapper;

import java.util.List;

import com.luban.entity.User;

public interface UserMapper {
	int deleteByPrimaryKey(Integer userId);

	int insert(User record);

	int insertSelective(User record);

	User selectByPrimaryKey(Integer userId);

	int updateByPrimaryKeySelective(User record);

	int updateByPrimaryKey(User record);

	User findById(Integer userId);

	List<User> findAll();
}