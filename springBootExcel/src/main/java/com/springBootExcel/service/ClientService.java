package com.springBootExcel.service;

import java.util.List;
import java.util.Map;

import com.springBootExcel.entity.Client;

public interface ClientService {

	public int insertClient(Client client);
	
	public List<Client> selectClientPage(Map<String, Object> map);
	
	public int selectCount();
	
	public List<Client> findAllClient();
	
	public int insertClientList(List<Client> client);
}
