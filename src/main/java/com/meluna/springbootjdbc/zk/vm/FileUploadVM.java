package com.meluna.springbootjdbc.zk.vm;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.collections.CaseInsensitiveKeyMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.io.Files;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.web.servlet.dsp.action.When;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meluna.springbootjdbc.pojos.ral.Allocation;
import com.meluna.springbootjdbc.pojos.ral.Timesheet;

public class FileUploadVM {

	private boolean fileuploaded = false;
	AMedia fileContent;

	@Wire("#test")
	private Window win;

	public AMedia getFileContent() {
		return fileContent;
	}

	public void setFileContent(AMedia fileContent) {
		this.fileContent = fileContent;
	}

	public boolean isFileuploaded() {
		return fileuploaded;
	}

	public void setFileuploaded(boolean fileuploaded) {
		this.fileuploaded = fileuploaded;
	}

	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view)
			  {
		Selectors.wireComponents(view, this, false);
	 
	}

	@Command
	@NotifyChange("fileuploaded")
	public void onUploadPDF(
			@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx)
			throws IOException {

		UploadEvent upEvent = null;
		Object objUploadEvent = ctx.getTriggerEvent();
		if (objUploadEvent != null && (objUploadEvent instanceof UploadEvent)) {
			upEvent = (UploadEvent) objUploadEvent;
		}
		if (upEvent != null) {
			Media media = upEvent.getMedia();
			importToDB(media);
			
			Messagebox.show("File "+ media.getName() + "sucessfully uploaded");
			fileuploaded = true;
			
		}
	}
	
	public void importToDB(Media media)
	{
		try
		{
			XSSFWorkbook workbookIN  = new XSSFWorkbook( media.getStreamData());
			XSSFSheet sheetIN =  workbookIN.getSheetAt(1);
			List<Timesheet> list = readSheet(sheetIN);
			
			String resourceUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/timesheet/").toUriString();
			
			 for(Timesheet tsheet : list) {
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<String> result = restTemplate.postForEntity(resourceUri, tsheet, String.class);
		      }
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
			
	}
		
		
	private  List<Timesheet> readSheet(XSSFSheet sheet) throws ParseException{
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
		              case 0:
						timesheet.setResource((String)getValueFromCell(cell));
						break;					
		              case 3:
						timesheet.setProject((String)getValueFromCell(cell));
						break;
		              case 4:
						timesheet.setID((Double)getValueFromCell(cell));
						break;
		              case 6:
						timesheet.setTotalHours((Double)getValueFromCell(cell));
						break;	
		              case 7:
						timesheet.setTmsDate((Date)getValueFromCell(cell));
						break;
		              case 8:
						timesheet.setLastModifiedDate((Date)getValueFromCell(cell));
						break;	
		              case 9:
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
	
	 // Utility method to get cell value based on cell type
	  private Object getValueFromCell(Cell cell) {
	      switch(cell.getCellType()) {
	          case STRING:
	              return cell.getStringCellValue();
	          case  BOOLEAN:
	              return cell.getBooleanCellValue();
	          case  NUMERIC:
	              if(DateUtil.isCellDateFormatted(cell)) {
	                  return cell.getDateCellValue();
	              }
	              return cell.getNumericCellValue();
	          case  FORMULA:
	              return cell.getCellFormula();
	          case  BLANK:
	              return null;
	          default:
	              return null;                                
	      }
	  }

//	@Command
//	@NotifyChange("fileContent")
//	public void showPDF() throws IOException {
//		File f = new File(filePath);
//		Messagebox.show(" dfdfdfdsfdsf" + filePath);
//		byte[] buffer = new byte[(int) f.length()];
//		FileInputStream fs = new FileInputStream(f);
//		fs.read(buffer);
//		fs.close();
//		ByteArrayInputStream is = new ByteArrayInputStream(buffer);
//		fileContent = new AMedia("report", "pdf", "application/pdf", is);
//		 
//		 
//
//	}
}