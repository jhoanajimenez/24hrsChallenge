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
	
}
