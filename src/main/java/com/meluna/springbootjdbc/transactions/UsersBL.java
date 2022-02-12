package com.meluna.springbootjdbc.transactions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meluna.springbootjdbc.pojos.Users;

@Service
public class UsersBL {
	
	@Autowired	
	JdbcTemplate jdbcTemplate;
	
	@Transactional(rollbackFor = Exception.class)
	public List<Users> getUsersRows() throws Exception
	{
		String sql = "select userid, first_name, last_name from meluna.users where userid=?";
		
		List<Users> listRows = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Users>(Users.class), "gg01");
		
		return listRows;
	}
	
	

}


