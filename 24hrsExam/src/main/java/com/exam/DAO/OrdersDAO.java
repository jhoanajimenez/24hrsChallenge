package com.exam.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	
	@Transactional //rollback upon runtime error
	public void insertOrder(Orders order) throws SQLException {
		String order_query = "INSERT INTO orders(date,time) VALUES(:date,:time)";
		String order_details_query = "INSERT INTO order_details(order_id, pizza_id, quantity) VALUES (?,?,?)";
		
		//retrieve order_id on insert
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbc.getDataSource());
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
        
        Map<String, Object> params = new HashMap<>();
        params.put("date", LocalDate.now());
        params.put("time", LocalTime.now());
        int rowsAffected = namedParameterJdbcTemplate.update(order_query, new MapSqlParameterSource(params), generatedKeyHolder);
        
        int order_id = generatedKeyHolder.getKey().intValue();
        
        //insert order_details with order_id retrieved
        Object[] param = {
        		order_id, order.getPizza_id(), order.getQuantity()
        };
        
        jdbc.update(order_details_query,param);
     
	}
	
	
}
