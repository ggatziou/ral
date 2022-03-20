package com.meluna.springbootjdbc.zk.model;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.meluna.springbootjdbc.pojos.ral.Allocation;
import com.meluna.springbootjdbc.pojos.ral.Demand;
import com.meluna.springbootjdbc.pojos.ral.DemandKeyword;
import com.meluna.springbootjdbc.pojos.ral.PrjComment;
import com.meluna.springbootjdbc.pojos.ral.Resource;


public class ProjectData {
	
	List<Demand> demandList;
	


	public List<Demand> searchDemand(String keyword) 
	{
		
		DemandKeyword dk = new DemandKeyword();
		dk.setSearchKey(keyword);
		
		
		RestTemplate restTemplate = new RestTemplate();
//		String resourceUri0 = "http://localhost:8080/demand";
		String resourceUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/demandsearch/").toUriString(); 

//		ResponseEntity<Demand[]> response = restTemplate.getForEntity(resourceUri, Demand[].class);
		
		if(keyword==null ||(keyword!=null && keyword.trim().length()==0))
			return allDemand();
   	
		ResponseEntity<Demand[]> response = restTemplate.postForEntity(resourceUri, dk, Demand[].class);
		
		
		Demand[] demandArray = response.getBody();
		
		List<Demand> list = Arrays.asList(demandArray);
		
		
		return list;		
	}
	
	//TODO Να φτιαξω το api Να επιστρέφει με search
	public List<Demand> allDemand() 
	{
		
		RestTemplate restTemplate = new RestTemplate();
//			String resourceUri0 = "http://localhost:8080/demand";
		String resourceUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/demand/").toUriString(); 

		ResponseEntity<Demand[]> response = restTemplate.getForEntity(resourceUri, Demand[].class);
		Demand[] demandArray = response.getBody();
		
		List<Demand> list = Arrays.asList(demandArray);   
		
		return list;		
	}
	
	public List<Resource> allResources() 
	{
		
		RestTemplate restTemplate = new RestTemplate();
//			String resourceUri0 = "http://localhost:8080/demand";
		String resourceUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/resources/").toUriString(); 

		ResponseEntity<Resource[]> response = restTemplate.getForEntity(resourceUri, Resource[].class);
		Resource[] resourceArray = response.getBody();
		
		List<Resource> list = Arrays.asList(resourceArray);   
		
		return list;		
	}
	
	//TODO Να φτιαξω το api Να επιστρέφει με search
	public String allprjcommentsForId(String id) 
	{
		
		RestTemplate restTemplate = new RestTemplate();
//		String resourceUri0 = "http://localhost:8080/demand";
		String resourceUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/allprjcommentslist/"+id).toUriString(); 

		ResponseEntity<PrjComment[]> response = restTemplate.getForEntity(resourceUri, PrjComment[].class);
		PrjComment[] commentsArray = response.getBody();
		
		List<PrjComment> list = Arrays.asList(commentsArray);   
		
		StringBuilder sb = new StringBuilder();
		
		for(PrjComment comment:list)
		{
			sb.append(comment.getInsertedAt());
			sb.append("\r\n");
			sb.append(comment.getComment());
			sb.append("\r\n");
			sb.append("\r\n");
			
		}
		
		return sb.toString();		
	}


}
