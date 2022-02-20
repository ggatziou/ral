package com.meluna.springbootjdbc.zk.model;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.meluna.springbootjdbc.pojos.ral.Allocation;


public class AllocationData {
	
	List<Allocation> allocationList;
	

	//TODO Να φτιαξω το api Να επιστρέφει με search
	public List<Allocation> search(String keyword) 
	{
		
		RestTemplate restTemplate = new RestTemplate();
		String resourceUri0 = "http://localhost:8080/allocation";
		String resourceUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/allocation/").toUriString(); 

		ResponseEntity<Allocation[]> response = restTemplate.getForEntity(resourceUri, Allocation[].class);
		Allocation[] allocations = response.getBody();
		
		List<Allocation> list = Arrays.asList(allocations);   
		
		return list;

		
	}

}
