package com.meluna.springbootjdbc.pojos.ral;

import java.util.Date;

import lombok.Data;

public @Data class Demand {
	
	String title;	//Project title
	Double ID;	//Project ID
	String status;
	Date startDate;
	Date targetDate;
	Date completionDate;
	Boolean favorite;

}
