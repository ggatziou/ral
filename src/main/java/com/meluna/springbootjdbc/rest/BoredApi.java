package com.meluna.springbootjdbc.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.meluna.springbootjdbc.pojos.Activity;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class BoredApi {
	

	@GetMapping("/activity")
	String getActivity()
	{
		
		RestTemplate restTemplate = new RestTemplate();
		
		String resourceUrl = "https://www.boredapi.com/api/activity";
		ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl , String.class);
		
		return response.getBody();
	}
	
	@GetMapping("/activity2")
	String getActivity2()
	{
		
		RestTemplate restTemplate = new RestTemplate();
		
		String resourceUrl = "https://www.boredapi.com/api/activity";
		
		Activity activity = restTemplate.getForObject(resourceUrl, Activity.class);
				
		return activity.toString();
	}
	
	@GetMapping("/activity3")
	Activity getActivity3()
	{
		
		RestTemplate restTemplate = new RestTemplate();
		
		String resourceUrl = "https://www.boredapi.com/api/activity";
		
		Activity activity = restTemplate.getForObject(resourceUrl, Activity.class);
				
		return activity;
	}
	
	

}
