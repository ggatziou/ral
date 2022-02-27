package com.meluna.springbootjdbc.transactions.ral;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.meluna.springbootjdbc.pojos.ral.Allocation;
import com.meluna.springbootjdbc.pojos.ral.Demand;
import com.meluna.springbootjdbc.pojos.ral.Timesheet;

@Service
public class RalBL {
	
	@Autowired	
	JdbcTemplate jdbcTemplate;
	
	@Transactional(rollbackFor = Exception.class)
	public List<Allocation> getAllocationRows() throws Exception
	{
		String sql = "select a.AllocationId , a.Personid , a.Projectid , a.endDate ,p.PlanviewProjectId, p.Title, p2.FirstName, p2.LastName  \r\n" + 
				"from ral.allocation a \r\n" + 
				"join ral.projects p on a.Projectid =p.Projectid \r\n" + 
				"join ral.persons p2 on a.Personid =p2.Personid ";
		
		List<Allocation> listRows = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Allocation>(Allocation.class));
		
		return listRows;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<Allocation> getAllocationRowsById(String allocId) throws Exception
	{
		String sql = "select a.AllocationId , a.Personid , a.Projectid , a.endDate ,p.PlanviewProjectId, p.Title, p2.FirstName, p2.LastName  \r\n" + 
				"from ral.allocation a \r\n" + 
				"join ral.projects p on a.Projectid =p.Projectid \r\n" + 
				"join ral.persons p2 on a.Personid =p2.Personid "
				+ " where AllocationId=?";
		
		List<Allocation> listRows = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Allocation>(Allocation.class), Integer.valueOf(allocId));
		
		return listRows;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void saveTimesheet(Timesheet newTimesheet) throws Exception
	{
		
		if(newTimesheet.getID()==null)
	 	{
			return;
	 	}
		
		String sql = "insert into ral.timesheet values(?,?,?,?,?,?,?) ";		
		
		jdbcTemplate.update(new PreparedStatementCreator() {
	        @Override
	        public PreparedStatement createPreparedStatement(final Connection conn) throws SQLException {
	            final PreparedStatement ret = conn.prepareStatement(sql);
	            ret.setObject(1, newTimesheet.getResource());
	            ret.setObject(2, newTimesheet.getProject());
	            ret.setObject(3, newTimesheet.getID());
	            ret.setObject(4, newTimesheet.getTotalHours());
	            ret.setObject(5, newTimesheet.getTmsDate());
	            ret.setObject(6, newTimesheet.getLastModifiedDate());
	            ret.setObject(7, newTimesheet.getMonth());
	            return ret;
	        }
	    });
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void saveDemand(Demand newDemand) throws Exception
	{
		
		if(newDemand.getID()==null)
	 	{
			return;
	 	}
		
		String sql = "insert into ral.demand values(?,?,?,?,?,?) ";		
		
		jdbcTemplate.update(new PreparedStatementCreator() {
	        @Override
	        public PreparedStatement createPreparedStatement(final Connection conn) throws SQLException {
	            final PreparedStatement ret = conn.prepareStatement(sql);
	            ret.setObject(1, newDemand.getID());
	            ret.setObject(2, newDemand.getTitle());
	            ret.setObject(3, newDemand.getStatus());
	            ret.setObject(4, newDemand.getStartDate());
	            ret.setObject(5, newDemand.getTargetDate());
	            ret.setObject(6, newDemand.getCompletionDate());
	            
	            return ret;
	        }
	    });
	}
	

}
