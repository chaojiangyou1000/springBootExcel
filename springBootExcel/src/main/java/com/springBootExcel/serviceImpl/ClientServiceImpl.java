package com.springBootExcel.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springBootExcel.entity.Client;
import com.springBootExcel.mapper.ClientMapper;
import com.springBootExcel.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService{

	@Autowired
	private ClientMapper clientMapper;
	
	@Override
	public int insertClient(Client client) {
		// TODO Auto-generated method stub
		return clientMapper.insert(client);
	}

	@Override
	public List<Client> selectClientPage(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return clientMapper.selectClientPage(map);
	}

	@Override
	public int selectCount() {
		// TODO Auto-generated method stub
		return clientMapper.selectCount();
	}

	@Override
	public List<Client> findAllClient() {
		// TODO Auto-generated method stub
		return clientMapper.findAllClient();
	}

	@Override
	public int insertClientList(List<Client> client) {
		// TODO Auto-generated method stub
		return clientMapper.insertClientList(client);
	}

}
