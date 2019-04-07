package com.springBootExcel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/index")
public class IndexController {
	
	@RequestMapping("/manage")
	public String pageString(){
		return "client_manage";
	}
	
	@RequestMapping("/add")
	public String pagesString(){
		return "add_update";
	}
}
