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
import org.zkoss.bind.annotation.BindingParam;
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

public abstract class FileUploadVM {
	
	protected abstract String getApiPath();

	@AfterCompose
	public void initSetup(@ContextParam(ContextType.VIEW) Component view)
			  {
		Selectors.wireComponents(view, this, false);
	 
	}

	@Command
	@NotifyChange("fileuploaded")
	public void onUploadPDF(
			@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx
//			, @BindingParam("excel_type") Integer excel_type
			)
			throws IOException {

		UploadEvent upEvent = null;
		Object objUploadEvent = ctx.getTriggerEvent();
		if (objUploadEvent != null && (objUploadEvent instanceof UploadEvent)) {
			upEvent = (UploadEvent) objUploadEvent;
		}
		if (upEvent != null) {
			Media media = upEvent.getMedia();
			

				importToDB(media, getApiPath());
			
			Messagebox.show("File "+ media.getName() + "sucessfully uploaded");
			
		}
	}
	

	
	public void importToDB(Media media, String excel_type)
	{
		try
		{
			XSSFWorkbook workbookIN  = new XSSFWorkbook( media.getStreamData());
			XSSFSheet sheetIN =  workbookIN.getSheetAt(1);
			List<Object> list = readSheet(sheetIN);
			
			String resourceUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/"+excel_type+"/").toUriString();
			
			 for(Object tsheet : list) {
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<String> result = restTemplate.postForEntity(resourceUri, tsheet, String.class);
		      }
			
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
			
	}
	
	protected abstract<T> List<T> readSheet(XSSFSheet sheet) throws ParseException;
		
	
	 // Utility method to get cell value based on cell type
	  protected Object getValueFromCell(Cell cell) {
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

}