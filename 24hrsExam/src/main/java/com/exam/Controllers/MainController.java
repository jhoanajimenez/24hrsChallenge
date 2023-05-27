package com.exam.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.DAO.OrdersDAO;

@RestController
public class MainController {

	@Autowired
	OrdersDAO orderRepo;
	
	@GetMapping("/testing")
	public String testing() {
		return "TEST SUCCESS!";
	}
	
	
	
	//order all details
	//show all pizza category
	//get all pizza by category
	//all pizza names with available sizes
	//get pizza by name: show ingr, type , show price is each size 
	
	//filter pizza by size | category

	
	//POST order
	
	//aditional
	//add login
	//add status to orders
	//get order detail by user 
	
	
	
	
}
