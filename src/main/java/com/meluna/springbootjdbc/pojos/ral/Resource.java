package com.meluna.springbootjdbc.pojos.ral;

import lombok.Data;

@Data
public class Resource {

	Integer id;
	Long plvId;
	String code;
	String name;
	String nameGR;
	Boolean isActive;
	String companyCode;
	
	
}
