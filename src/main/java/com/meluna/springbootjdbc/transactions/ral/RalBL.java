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

import com.meluna.springbootjdbc.Const;
import com.meluna.springbootjdbc.pojos.ral.Allocation;
import com.meluna.springbootjdbc.pojos.ral.Demand;
import com.meluna.springbootjdbc.pojos.ral.PrjComment;
import com.meluna.springbootjdbc.pojos.ral.PrjMisc;
import com.meluna.springbootjdbc.pojos.ral.Resource;
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
	
	
	
	
	@Transactional(rollbackFor = Exception.class)
	public List<Resource> getResourceRows() throws Exception
	{
		
		StringBuffer  sqlSB = new StringBuffer();
		sqlSB.append("select * from ral.resources ");
		sqlSB.append("order  by name asc");
		
		List<Resource> listRows = null;
		
			listRows = jdbcTemplate.query(sqlSB.toString(), new BeanPropertyRowMapper<Resource>(Resource.class));
		
			
	
		return listRows;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<Demand> getDemandRows(String keyword) throws Exception
	{
		
		StringBuffer  sqlSB = new StringBuffer();
		sqlSB.append("select d.*, case when m.prid is not null then true else false end as favorite ");
		sqlSB.append("from ral.demand d ");
		sqlSB.append("left join prjmisc m on d.id = m.prid and m.miscCode='favorite' ");
		sqlSB.append("where title not in ('ADMIN - Applications', ");
		sqlSB.append("'SUP - Corporate Loan - L.O.S.', ");
		sqlSB.append("'SUP - Corporate Loan - CSS', ");
		sqlSB.append("'SUP - Corporate Loan - Peripheral Appl.', ");
		sqlSB.append("'DATA - Corporate Loan Origination Systems')");
		sqlSB.append(" and d.status in ( 'On Hold', 'In progress', 'Not started')");
		
		//, 'Rejected' 'Completed','Canceled',
		List<Demand> listRows = null;
		
		
		
		if(keyword!=null && keyword.trim().length()>0)
		{
			keyword = keyword
				    .replace("!", "!!")
				    .replace("%", "!%")
				    .replace("_", "!_")
				    .replace("[", "![");
			
			sqlSB.append("and (title like ? OR d.id = ?)");
			sqlSB.append("order  by status asc, startDate DESC");
			
			String ps_keyword = "%" + keyword.trim() + "%";

			String s_keyword = keyword.trim();
			
//			Long l_keyword = 0L;
//			
//			
//			
//					Long.valueOf(keyword.trim());
			
			listRows = jdbcTemplate.query(sqlSB.toString(), new BeanPropertyRowMapper<Demand>(Demand.class), ps_keyword, s_keyword);
		}
		else
		{
			sqlSB.append("order  by status asc, startDate DESC");
			listRows = jdbcTemplate.query(sqlSB.toString(), new BeanPropertyRowMapper<Demand>(Demand.class));
		}
			
	
		return listRows;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public List<Demand> getDemandRows() throws Exception
	{
		
		return getDemandRows(null);
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	public List<PrjComment> getAllPrjComments(String projectId) throws Exception
	{
		String sql = "select  * from ral.prjcomments p \r\n" + 
				"where p.prid = ?"
				+ "order by insertedAt desc";
		
		List<PrjComment> listRows = jdbcTemplate.query(sql, new BeanPropertyRowMapper<PrjComment>(PrjComment.class), Double.valueOf(projectId));
		return listRows;

		
		//DEPRECATED
//		 return jdbcTemplate.query(sql, new Object[]{projectId}, (rs, rowNum) ->
//         new PrjComment(
//                 rs.getInt("id"),
//                 rs.getDouble("prid"),
//                 rs.getString("comment"),
//                 rs.getTimestamp("insertedAt")
//         ));
		
		


	}
	
	@Transactional(rollbackFor = Exception.class)
	public void savePrjComment(PrjComment newComment) throws Exception
	{
		
		if(newComment.getPrid()==0)
	 	{
			return;
	 	}
		
		String sql = "insert into ral.prjcomments(prid, comment, insertedAt) values(?,?,current_timestamp) ";		
		
		jdbcTemplate.update(new PreparedStatementCreator() {
	        @Override
	        public PreparedStatement createPreparedStatement(final Connection conn) throws SQLException {
	            final PreparedStatement ret = conn.prepareStatement(sql);
	            ret.setObject(1, newComment.getPrid());
	            ret.setObject(2, newComment.getComment());
	            
	            return ret;
	        }
	    });
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	public void updatePrjMisc(PrjMisc prjMisc) throws Exception
	{
		
		if(prjMisc.getAction().equals(Const.ACTION_INSERT))
		{
			
			String sql = "insert into prjmisc(prid,miscCode, insertedAt ) values (?, ?, current_timestamp )\r\n" + 
					"ON DUPLICATE KEY UPDATE id=id ";		
			
			jdbcTemplate.update(new PreparedStatementCreator() {
		        @Override
		        public PreparedStatement createPreparedStatement(final Connection conn) throws SQLException {
		            final PreparedStatement ret = conn.prepareStatement(sql);
		            ret.setObject(1, prjMisc.getPrid());
		            ret.setObject(2, prjMisc.getMiscCode());
		            
		            return ret;
		        }
		    });
			
			
		}
		else if (prjMisc.getAction().equals(Const.ACTION_DELETE))
		{
			String sql = "delete from prjmisc where prid = ? ";		
			
			jdbcTemplate.update(new PreparedStatementCreator() {
		        @Override
		        public PreparedStatement createPreparedStatement(final Connection conn) throws SQLException {
		            final PreparedStatement ret = conn.prepareStatement(sql);
		            ret.setObject(1, prjMisc.getPrid());		            
		            
		            return ret;
		        }
		    });
		}		
		
	}

}
