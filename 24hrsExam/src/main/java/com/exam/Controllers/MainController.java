package com.exam.Controllers;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.DAO.OrdersDAO;
import com.exam.DAO.UserDAO;
import com.exam.Models.User;

@RestController
public class MainController {

	@Autowired
	UserDAO userDao;
	
	@GetMapping("/testing")
	public String testing() {
		return "TEST SUCCESS!";
	}
	
	@PostMapping("/login")
	public HashMap<String,String> login(@RequestBody User user){
		//verify user
		if(userDao.verifyUser(user)) {
			
		}
		return null;
	}
	
	
	
	//aditional
	//add login
	//add status to orders
	//get order detail by user 
	
	
	
	
}
