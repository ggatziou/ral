package com.meluna.springbootjdbc.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.meluna.springbootjdbc.pojos.ral.Timesheet;
import com.meluna.springbootjdbc.transactions.ral.RalBL;


//@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TimesheetRest {
	
	@Autowired
	RalBL ralBL;
	
	
//	both annotations do the same 
//	@PostMapping("/timesheet")
	@RequestMapping(value="/timesheet",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	Timesheet newTimesheet(@RequestBody Timesheet newTimesheet )
	{		
		 try {			 	
			 	ralBL.saveTimesheet(newTimesheet);
			 	return newTimesheet;
			 	            
	        } catch (Exception e) {
	            // transaction has already been rolled back.
	            //result = e.getMessage();
	        	e.printStackTrace();
	        }
		 
		 return new Timesheet();
	}
}
