package com.exam.DAO;

import java.sql.SQLException;
import java.util.ArrayList;import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.exam.Models.Pizzas;

@Component
public class PizzaDAO {
	
	@Autowired
	JdbcTemplate jdbc;
	
	public HashMap<String,Object> getPizzaDetails(String pizza_type) throws SQLException{
		String query = "SELECT pizza_id, p.pizza_type, NAME, category, ingredients, size, price FROM pizza_types pt "
				+ "JOIN pizzas p ON pt.pizza_type = p.pizza_type "
				+ "WHERE p.pizza_type = ?";
		
		HashMap<String,Object> hmap = new HashMap<String,Object>();
		
		List<Pizzas> list = new ArrayList<Pizzas>();
		SqlRowSet rs = jdbc.queryForRowSet(query, pizza_type);
		
		int counter = 0;
		while(rs.next()) {
			if(counter == 0) {
				hmap.put("pizza_type", rs.getString("pizza_type"));
				hmap.put("name", rs.getString("name"));
				hmap.put("category", rs.getString("category"));
				hmap.put("ingredients", rs.getString("ingredients"));
			
				counter++;
			}
			
			Pizzas pizza = new Pizzas();
			pizza.setPizza_id(rs.getString("pizza_id"));
			pizza.setSize(rs.getString("size"));
			pizza.setPrice(rs.getDouble("price"));

			list.add(pizza);
			
		}
		hmap.put("sizes", list);
		
		return hmap;
	}

	
	public List<String> getCategories() throws SQLException{
		String query = "SELECT DISTINCT category FROM pizza_types";
		
		List<String> list = new ArrayList<String>();
		SqlRowSet rs = jdbc.queryForRowSet(query);
		while(rs.next()) {
			list.add(rs.getString("category"));
		}
		
		return list;
	}

	public List<Pizzas> getPizzaByCategory(String category) throws SQLException{
		String filter = category == null ? "" : "WHERE category = ?";
		
		String query = "SELECT p.pizza_type, name, GROUP_CONCAT(size ORDER BY size ASC) sizes FROM pizza_types pt "
				+ "JOIN pizzas p ON p.pizza_type = pt.pizza_type "
				+ filter + "GROUP BY name";
		
		List<Pizzas> list = new ArrayList<Pizzas>();
		SqlRowSet rs = null;
		
		if(category == null ) {
			rs = jdbc.queryForRowSet(query);
		} else {
			rs = jdbc.queryForRowSet(query, category);
		}
		
		while(rs.next()) {
			Pizzas pizza = new Pizzas();
			pizza.setType(rs.getString("pizza_type"));
			pizza.setName(rs.getString("name"));
			pizza.setSize(rs.getString("sizes"));
			
			list.add(pizza);
		}
		
		return list;
	}
}
