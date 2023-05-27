package com.exam.Controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.DAO.OrdersDAO;
import com.exam.Models.Orders;

@RestController
@RequestMapping("/orders")
public class OrdersController {
	
	@Autowired
	OrdersDAO orderDAO;
	
	@GetMapping("/testing")
	public String testing() {
		return "TEST SUCCESS!";
	}
	
	/**
	 * 
	 * @return Orders JSON
	 */
	@GetMapping("/all")
	public HashMap<String,Object> getOrders() {
		HashMap<String,Object> resp = new HashMap<String,Object>();
		try {
			resp.put("data", orderDAO.getOrders());
		} catch (SQLException e) {
			e.printStackTrace();
			resp.put("Error", "Error retrieveing orders...");
		}
		return resp;
	}
	
	
	/**
	 * get the order details by order_id
	 * @return Orders Details JSON
	 */
	@GetMapping("/details")
	public HashMap<String,Object> getOrderDetails(@RequestParam int orderid) {
		HashMap<String, Object> resp = new HashMap<String, Object>();
		try {
			resp = orderDAO.getDetails(orderid);
		} catch (SQLException e) {
			e.printStackTrace();
			resp.put("Error", "Error retrieveing details...");
		}
		return resp;
	}
	
	

	

	//POST order
	
	//aditional
	//add login
	//add status to orders
	//get order detail by user 
	
	

}
