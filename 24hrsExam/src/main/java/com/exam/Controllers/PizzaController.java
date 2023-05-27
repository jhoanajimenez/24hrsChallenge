package com.exam.Controllers;

import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exam.DAO.PizzaDAO;

@RestController
@RequestMapping("/pizza")
public class PizzaController {
	
	@Autowired
	PizzaDAO pizzaDAO;
	
	/**
	 * 
	 * @return names list JSON
	 */
	@GetMapping("/all")
	public HashMap<String,Object> getAllNames(){
		HashMap<String,Object> resp = new HashMap<String,Object>();
		
		try {
			resp.put("data", pizzaDAO.getPizzaByCategory(null));
		} catch (SQLException e) {
			e.printStackTrace();
			resp.put("Error", "Error retrieveing pizzas...");
		}
		return resp;
	}
	
	/**
	 * 
	 * @return category list JSON
	 */
	@GetMapping("/categories")
	public HashMap<String,Object> getAllCategory(){
		HashMap<String,Object> resp = new HashMap<String,Object>();
		
		try {
			resp.put("data", pizzaDAO.getCategories());
		} catch (SQLException e) {
			e.printStackTrace();
			resp.put("Error", "Error retrieveing pizzas...");
		}
		return resp;
	}
	
	/**
	 * 
	 * @param category
	 * @return pizza list JSON
	 */
	@GetMapping("/view")
	public HashMap<String,Object> getPizzaByCategory(@RequestParam String category){
		HashMap<String,Object> resp = new HashMap<String,Object>();
		
		try {
			resp.put("data", pizzaDAO.getPizzaByCategory(category));
		} catch (SQLException e) {
			e.printStackTrace();
			resp.put("Error", "Error retrieveing pizzas...");
		}
		return resp;
	}
	
	
	/**
	 * 
	 * @param id
	 * @return pizza details JSON
	 */
	@GetMapping("/viewPizza")
	public HashMap<String,Object> getPizzaDetails(@RequestParam String type){
		HashMap<String,Object> resp = new HashMap<String,Object>();
		
		try {
			resp = pizzaDAO.getPizzaDetails(type);
		} catch (SQLException e) {
			e.printStackTrace();
			resp.put("Error", "Error retrieveing pizzas...");
		}
		return resp;
	}
	
}
