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
import com.meluna.springbootjdbc.pojos.ral.Timesheet;

public class TimesheetExcelUploadVM extends FileUploadVM{

	
	@Override
	protected String getApiPath() {
		// TODO Auto-generated method stub
		return "timesheet";
	}
	
	
	@Override
	protected  List<Timesheet> readSheet(XSSFSheet sheet) throws ParseException{
	      Iterator<Row> rowItr = sheet.iterator();
	      List<Timesheet> list = new ArrayList<Timesheet>();
	      
	      String month = "2022 - 01";
	      
	      // Iterate through rows
	      while(rowItr.hasNext()) {
	          
	          Row row = rowItr.next();
	          // skip header (First row)
	          if(row.getRowNum() < 1) {
	              continue;
	          }
	          
	       	          
	          Iterator<Cell> cellItr = row.cellIterator();
	          Timesheet timesheet  = new Timesheet();
	          
	          // Iterate each cell in a row
	          while(cellItr.hasNext()) {           	
	              
		              Cell cell = cellItr.next();
		              		              
		              switch (cell.getColumnIndex()) {
		              case 1:
						timesheet.setResource((String)getValueFromCell(cell));
						break;					
		              case 7:
						timesheet.setProject((String)getValueFromCell(cell));
						break;
		              case 8:
						timesheet.setID((Double)getValueFromCell(cell));
						break;
		              case 18:
						timesheet.setTotalHours((Double)getValueFromCell(cell));
						break;	
		              case 19:
						timesheet.setTmsDate((Date)getValueFromCell(cell));
						break;
		              case 20:
						timesheet.setLastModifiedDate((Date)getValueFromCell(cell));
						break;	
		              case 21:
						timesheet.setMonth((String)getValueFromCell(cell));
						break;
	             
					default:
						break;
					}              
	          
	          }
	          
	          if(timesheet.getMonth().equals("2022 - 01") || timesheet.getMonth().equals("2022 - 02"))
	          {
	        	  list.add(timesheet);  
	          }
	          
	      }
	      
	      return list;
	}   
}
