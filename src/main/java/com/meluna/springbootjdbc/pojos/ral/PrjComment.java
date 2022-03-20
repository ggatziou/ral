package com.meluna.springbootjdbc.pojos.ral;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrjComment {
	
	int id;
	long prid;
	String comment;
	Timestamp insertedAt;

}
