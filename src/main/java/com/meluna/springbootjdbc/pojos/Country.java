package com.meluna.springbootjdbc.pojos;

import lombok.Data;

@Data
public class Country {
	String iso2Code;
	String name;
	String region; 
	String adminregion; 
	String incomeLevel;
	String lendingType; 
	String capitalCity;
	Double longitude;
	Double latitude;
	

}
