package com.meluna.springbootjdbc.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.meluna.springbootjdbc.pojos.ral.Allocation;
import com.meluna.springbootjdbc.transactions.ral.RalBL;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class AllocationRest {
	
	@Autowired
	RalBL ralBL;
	
	@GetMapping("/allocation")
	List<Allocation> getAllAllocationData() {
		List<Allocation> allocationRows = new ArrayList<Allocation>();
        try {
        	allocationRows = ralBL.getAllocationRows();            
        } catch (Exception e) {
            // transaction has already been rolled back.
            //result = e.getMessage();
        	e.printStackTrace();
        }

        return allocationRows;
				
	}
	
	@GetMapping("/allocation/{id}")
	@ResponseBody
	public List<Allocation> getAllAllocationDataById(@PathVariable String id) {
		List<Allocation> allocationRows = new ArrayList<Allocation>();
        try {
        	allocationRows = ralBL.getAllocationRowsById(id);            
        } catch (Exception e) {
            // transaction has already been rolled back.
            //result = e.getMessage();
        	e.printStackTrace();
        }

        return allocationRows;
	}
}
