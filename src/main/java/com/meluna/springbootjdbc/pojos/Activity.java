package com.meluna.springbootjdbc.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Activity {
	
//	{"activity":"Go to a local thrift shop",
//		"type":"recreational",
//		"participants":1,
//		"price":0.1,
//		"link":"",
//		"key":"8503795",
//		"accessibility":0.2
//	}
	
	
	@JsonProperty("activity")
	String drastiriotita;
	@JsonProperty("type")
	String typos;
	@JsonProperty("participants")
	int symmetexontes;
	@JsonProperty("price")
	double timh;
	@JsonProperty("link")
	String link;
	@JsonProperty("key")
	String kleidi;
	@JsonProperty("accessibility")
	double prosvasimotita;
	
	@Override
	public String toString() {
		
		return typos+" - "+drastiriotita;
	}
		

}
