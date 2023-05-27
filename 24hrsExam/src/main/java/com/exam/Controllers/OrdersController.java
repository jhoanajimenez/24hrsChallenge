package com.exam.Controllers;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.DAO.OrdersDAO;
import com.exam.Models.Orders;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	/**
	 * 
	 * @param {order : [pizza_id, quantity]}
	 * @return
	 */
	@PostMapping("/create")
	public HashMap<String,Object> createOrder(@RequestBody Map<String,Object> order) {
		HashMap<String,Object> hmap = new HashMap<String,Object>();
		
		ObjectMapper objectMapper = new ObjectMapper();

        try {
            String order_str = objectMapper.writeValueAsString(order);
            JSONObject obj = new JSONObject(order_str);
            JSONArray orders_arr = obj.getJSONArray("orders");
            
            //iterate all orders
            for(int i=0; i < orders_arr.length(); i++) {
            	JSONObject o_obj = orders_arr.getJSONObject(i);
            	
            	Orders ord = new Orders();
            	ord.setPizza_id(o_obj.getString("pizza_id"));
            	ord.setQuantity(o_obj.getInt("quantity"));
            	
            	//insert each order
            	orderDAO.insertOrder(ord);
            }
            
            hmap.put("Success", "Order created");
            
      
        } catch (Exception e) {
			e.printStackTrace();
			hmap.put("Error", "Error on creating order");
		}
		
		return hmap;
	}
	
	

	

	//POST order
	
	//aditional
	//add login
	//add status to orders
	//get order detail by user 
	
	

}
