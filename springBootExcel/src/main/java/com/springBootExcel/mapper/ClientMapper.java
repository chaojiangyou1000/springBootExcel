package com.springBootExcel.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.springBootExcel.entity.Client;

@Mapper
public interface ClientMapper {
	int deleteByPrimaryKey(Integer id);

	int insert(Client record);

	int insertSelective(Client record);

	Client selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Client record);

	int updateByPrimaryKey(Client record);

	List<Client> selectClientPage(Map<String, Object> map);

	int selectCount();

	List<Client> findAllClient();

	int insertClientList(List<Client> client);
}