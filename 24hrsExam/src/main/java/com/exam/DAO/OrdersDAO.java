package com.exam.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.exam.Models.OrderDetails;
import com.exam.Models.Orders;

@Component
public class OrdersDAO{
	
	@Autowired
	JdbcTemplate jdbc;
	
	
	
	public List<Orders> getOrders() throws SQLException{
		String query = "SELECT o.order_id, DATE, TIME, SUM(quantity) total_qty, SUM((price*quantity)) total_amount "
				+ "FROM orders o "
				+ "JOIN order_details od ON o.order_id = od.order_id "
				+ "LEFT JOIN pizzas p ON od.pizza_id=p.pizza_id "
				+ "GROUP BY od.order_id";
;
		
		List<Orders> list = new ArrayList<Orders>();
		SqlRowSet rs = jdbc.queryForRowSet(query);
		while(rs.next()){
			Orders order = new Orders();
			order.setOrder_id(rs.getInt("order_id"));
			order.setDate(rs.getString("date"));
			order.setTime(rs.getString("time"));
			order.setQuantity(rs.getInt("total_qty"));
			order.setTotal_amount(rs.getDouble("total_amount"));
			
			
			list.add(order);
		}
		
		return list;
	}
	
	public HashMap<String,Object> getDetails(int id) throws SQLException{
		String query = "SELECT order_id, p.pizza_id, name, ingredients, size,  quantity, (quantity * price) total_price "
				+ "FROM order_details od "
				+ "LEFT JOIN pizzas p ON od.pizza_id = p.pizza_id "
				+ "LEFT JOIN pizza_types pt ON p.pizza_type = pt.pizza_type "
				+ "WHERE order_id = ? "
				+ "GROUP BY order_id,p.pizza_id";
		
		HashMap<String,Object> hmap = new HashMap<String,Object>();
		hmap.put("order_id", id);
		
		int total_qty = 0;
		double total_price = 0;
		
		List<OrderDetails> list = new ArrayList<OrderDetails>();
		SqlRowSet rs = jdbc.queryForRowSet(query,id);
		while(rs.next()){
			OrderDetails order = new OrderDetails();
			order.setPizza_name(rs.getString("name"));
			order.setIngredients(rs.getString("ingredients"));
			order.setSize(rs.getString("size"));
			order.setQuantity(rs.getInt("quantity"));
			order.setPrice(rs.getDouble("total_price"));
			
			list.add(order);
			
			total_qty += rs.getInt("quantity");
			total_price += rs.getDouble("total_price");
		}
		
        hmap.put("total_quantity", total_qty);
		hmap.put("total_price", total_price);
		hmap.put("orders", list);
		
		return hmap;
	}
	
	
}
