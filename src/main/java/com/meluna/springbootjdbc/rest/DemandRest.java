package com.meluna.springbootjdbc.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.meluna.springbootjdbc.pojos.ral.Allocation;
import com.meluna.springbootjdbc.pojos.ral.Demand;
import com.meluna.springbootjdbc.pojos.ral.DemandKeyword;
import com.meluna.springbootjdbc.pojos.ral.PrjComment;
import com.meluna.springbootjdbc.pojos.ral.PrjMisc;
import com.meluna.springbootjdbc.pojos.ral.Resource;
import com.meluna.springbootjdbc.pojos.ral.Timesheet;
import com.meluna.springbootjdbc.transactions.ral.RalBL;


//@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class DemandRest {
	
	@Autowired
	RalBL ralBL;
	
	
//	both annotations do the same 
//	@PostMapping("/demand")
	@RequestMapping(value="/demand",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	Demand newDemand(@RequestBody Demand newDemand )
	{		
		 try {			 	
			 	ralBL.saveDemand(newDemand);
			 	return newDemand;
			 	            
	        } catch (Exception e) {
	            // transaction has already been rolled back.
	            //result = e.getMessage();
	        	e.printStackTrace();
	        }
		 
		 return new Demand();
	}
	
	@RequestMapping(value="/demandsearch",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	List<Demand> getSearchDemandData(@RequestBody DemandKeyword dk) {
		List<Demand> demandRows = new ArrayList<Demand>();
        try {
        	demandRows = ralBL.getDemandRows(dk.getSearchKey());            
        } catch (Exception e) {
            // transaction has already been rolled back.
            //result = e.getMessage();
        	e.printStackTrace();
        }

        return demandRows;
				
	}
	
	@RequestMapping(value="/updprjmisc",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	void updateProjectMisc(@RequestBody PrjMisc prjMisc) {
		//List<Demand> demandRows = new ArrayList<Demand>();
        try {
        	
        	int ggg=9;
        	ralBL.updatePrjMisc(prjMisc);
        	//demandRows = ralBL.getDemandRows(dk.getSearchKey());            
        } catch (Exception e) {
            // transaction has already been rolled back.
            //result = e.getMessage();
        	e.printStackTrace();
        }       
				
	}
	
	
	
	@GetMapping("/demand")
	List<Demand> getAllDemandData() {
		List<Demand> demandRows = new ArrayList<Demand>();
        try {
        	demandRows = ralBL.getDemandRows();            
        } catch (Exception e) {
            // transaction has already been rolled back.
            //result = e.getMessage();
        	e.printStackTrace();
        }

        return demandRows;
				
	}
	
	@GetMapping("/resources")
	List<Resource> getAllResourcesData() {
		List<Resource> resourceRows = new ArrayList<Resource>();
        try {
        	resourceRows = ralBL.getResourceRows();            
        } catch (Exception e) {
            // transaction has already been rolled back.
            //result = e.getMessage();
        	e.printStackTrace();
        }

        return resourceRows;
				
	}
	
	
	
	@GetMapping("/allprjcommentslist/{id}")
	List<PrjComment> getAllPrjCommentsListForId(@PathVariable String id) {
		List<PrjComment> demandRows = new ArrayList<PrjComment>();
        try {
        	demandRows = ralBL.getAllPrjComments(id);            
        } catch (Exception e) {
            // transaction has already been rolled back.
            //result = e.getMessage();
        	e.printStackTrace();
        }

        return demandRows;
				
	}
	
	@RequestMapping(value="/prjcomment",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
	PrjComment newComment(@RequestBody PrjComment newComment )
	{		
		 try {			 	
			 	ralBL.savePrjComment(newComment);
			 	return newComment;
			 	            
	        } catch (Exception e) {
	            // transaction has already been rolled back.
	            //result = e.getMessage();
	        	e.printStackTrace();
	        }
		 
		 return new PrjComment();
	}
	
}
