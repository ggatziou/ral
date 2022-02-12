package com.meluna.springbootjdbc.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.meluna.springbootjdbc.pojos.Users;
import com.meluna.springbootjdbc.transactions.UsersBL;
/**
 * Rest API που φέρνει data από τη local database
 * @author ggatz
 *
 */
@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class UsersApi {
	
	@Autowired
	UsersBL usersBL;


	
	@GetMapping("/users")
	List<Users> getusers() {
		List<Users> usersRows = new ArrayList<Users>();
        try {
        	usersRows = usersBL.getUsersRows();            
        } catch (Exception e) {
            // transaction has already been rolled back.
            //result = e.getMessage();
        	e.printStackTrace();
        }

        return usersRows;
				
	}
	
	

}
