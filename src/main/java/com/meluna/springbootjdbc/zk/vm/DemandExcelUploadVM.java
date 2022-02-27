package com.meluna.springbootjdbc.zk.vm;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.meluna.springbootjdbc.pojos.ral.Demand;
import com.meluna.springbootjdbc.pojos.ral.Demand;

public class DemandExcelUploadVM extends FileUploadVM{
	
	@Override
	protected String getApiPath() {
		// TODO Auto-generated method stub
		return "demand";
	}
	
	
	@Override
	protected  List<Demand> readSheet(XSSFSheet sheet) throws ParseException{
	      Iterator<Row> rowItr = sheet.iterator();
	      List<Demand> list = new ArrayList<Demand>();
	      
	            
	      // Iterate through rows
	      while(rowItr.hasNext()) {
	          
	          Row row = rowItr.next();
	          // skip header (First row)
	          if(row.getRowNum() < 1) {
	              continue;
	          }
	          
	       	          
	          Iterator<Cell> cellItr = row.cellIterator();
	          Demand demand  = new Demand();
	          
	          // Iterate each cell in a row
	          while(cellItr.hasNext()) {           	
	              
		              Cell cell = cellItr.next();
		              		              
		              switch (cell.getColumnIndex()) {
		              case 0:
						demand.setID((Double)getValueFromCell(cell));
						break;					
		              case 1:
						demand.setTitle((String)getValueFromCell(cell));
						break;
		              case 3:
						demand.setStatus((String)getValueFromCell(cell));
						break;
		              case 8:
						demand.setStartDate((Date)getValueFromCell(cell));
						break;	
		              case 9:
		            	demand.setTargetDate((Date)getValueFromCell(cell));
						break;
		              case 10:
			            demand.setCompletionDate((Date)getValueFromCell(cell));
						break;
	             
					default:
						break;
					}              
	          
	          }
	          
	          
	          list.add(demand);  
	          
	          
	      }
	      
	      return list;
	}   
}
