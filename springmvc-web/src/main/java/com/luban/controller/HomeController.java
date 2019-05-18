package com.luban.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.luban.entity.User;
import com.luban.service.UserService;

/**
 * 使用JAVA类配置springMVC(不使用xml形式,本工程没有任何的xml文件)
 *
 * @author 晁江
 * @date:2019-05-17
 * @TODO:
 * @HomeController
 * @com.luban.controller
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		logger.info("~~~~~~~~~~~~~~~~进入Controller层了~~~~~~~~~~~~~~~~");
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
		return "home";
	}

	/**
	 * http://localhost:8080/springmvc-web/select/1
	 * 
	 * @param userId
	 * @return User
	 */
	@RequestMapping(value = "/select/{id}", method = RequestMethod.GET)
	@ResponseBody
	public User selectUserById(@PathVariable(name = "id") Integer userId) {
		User user = userService.findById(userId);
		logger.info("~~~~~~~~~~~~~~~~请求成功~~~~~~~~~~~~~~~~~~~~");
		return user;
	}

	/**
	 * 获取所有数据
	 * 
	 * @return List<User>
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public List<User> getAllUser() {
		return userService.findAll();
	}
}
