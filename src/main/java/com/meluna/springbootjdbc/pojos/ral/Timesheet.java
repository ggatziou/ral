package com.meluna.springbootjdbc.pojos.ral;

import java.util.Date;

import lombok.Data;

@Data
public  class Timesheet {
	
	String resource;
//	String role;	
//	String timeType;	
	String project;	//Project title
	Double ID;	//Project ID
//	String task;	
	Double totalHours;	
	Date tmsDate;	
	Date lastModifiedDate;	
	String month;	
//	String approvalState;
}
