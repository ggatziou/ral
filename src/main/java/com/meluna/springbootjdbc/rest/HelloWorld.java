package com.meluna.springbootjdbc.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * Rest API για testing
 * @author ggatz
 *
 */
@RestController
public class HelloWorld {
	
	@GetMapping("/")
	public String index(HttpServletRequest request) {
		  		  
		  return "Hello World!";
	}
}