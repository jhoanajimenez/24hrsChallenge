package com.exam.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import com.exam.Models.User;

@Component
public class UserDAO {

	@Autowired
	JdbcTemplate jdbc;
	
	public boolean verifyUser(User user) {
		String query = "SELECT * FROM users WHERE username = ? and password = ?";
		
		SqlRowSet rs = jdbc.queryForRowSet(query, user.getUser(), user.getPassword());
		if(rs.next()) {
			return true;
		}
			
		return false;
	}
}
