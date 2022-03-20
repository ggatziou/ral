package com.meluna.springbootjdbc.zk.vm;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.springframework.core.ConfigurableObjectInputStream;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.zkoss.bind.BindContext;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.BindingParams;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.ListModelList;

import com.meluna.springbootjdbc.Const;
import com.meluna.springbootjdbc.pojos.ral.Allocation;
import com.meluna.springbootjdbc.pojos.ral.Demand;
import com.meluna.springbootjdbc.pojos.ral.DemandResources;
import com.meluna.springbootjdbc.pojos.ral.PrjComment;
import com.meluna.springbootjdbc.pojos.ral.PrjMisc;
import com.meluna.springbootjdbc.pojos.ral.Resource;
import com.meluna.springbootjdbc.zk.model.ProjectData;

import lombok.Data;


public class ProjectsVM {
	
	private List<Demand> demandList = new ListModelList<Demand>();
	private List<PrjComment> prjCommentList = new ListModelList<PrjComment>();
	private List<DemandResources> demandResourcesList = new ListModelList<DemandResources>();
	
	private ListModelList<Resource> resourceList; 

	String captionLabelProject;
	
	
	String currentComments = "";
	String newComment = "";
	
	String txtStartDate ="";
	

	
	private Demand selectedDemand;
	
	
	private ProjectData projectData = new ProjectData();
	String keywordForDemand;
	
	
	@Init 
	public void startPage()
	{
		demandList.clear();
		demandList.addAll(projectData.allDemand());
		
		resourceList = new ListModelList<Resource>(projectData.allResources());
		resourceList.setMultiple(true);
		
		resetCaptionLabelProject();
		
		
	}
	
	private void resetCaptionLabelProject() {
		captionLabelProject = "Select a project to edit";
	}
	
	@Command
	@NotifyChange({ "currentComments", "captionLabelProject" , "selectedDemand" })
	public void searchDemand(){
		demandList.clear();
		demandList.addAll(projectData.searchDemand(keywordForDemand));
		
		resourceList.clear();
		resourceList.addAll(projectData.allResources());
		
		resetCaptionLabelProject();
		
		selectedDemand = null;
		currentComments = "";
		resetCaptionLabelProject();
		
	}
	
	@Command
	@NotifyChange({"currentComments", "newComment"})
	public void addComment(){
		
		String resourceUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/prjcomment/").toUriString();
		
			PrjComment prjComment = new PrjComment();
			prjComment.setPrid(selectedDemand.getID().longValue());
			prjComment.setComment(newComment);
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> result = restTemplate.postForEntity(resourceUri, prjComment, String.class);
			
			newComment = "";
			
			DecimalFormat df = new DecimalFormat("#");
			df.setMaximumFractionDigits(0);
			
			currentComments = projectData.allprjcommentsForId(df.format(selectedDemand.getID()));
	      				
	}
	
	@Command
	public void switchFavorite(
			@ContextParam(ContextType.BIND_CONTEXT) BindContext ctx,
			
			@BindingParam("favoriteId") Double favoriteId){
		
		String resourceUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/updprjmisc/").toUriString();

		Checkbox checkboxFavorite  = (Checkbox)ctx.getComponent();
		Boolean isChecked = checkboxFavorite.isChecked();
		
		
		PrjMisc prjMisc = new PrjMisc();
		prjMisc.setPrid(favoriteId.longValue());
		prjMisc.setMiscCode(Const.MISC_FAVORITE );
		
		if(isChecked)
			prjMisc.setAction(Const.ACTION_INSERT);
		else
			prjMisc.setAction(Const.ACTION_DELETE);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.postForEntity(resourceUri, prjMisc, String.class);
		
		
		
	}
	
	

	public Demand getSelectedDemand() {
		return selectedDemand;
	}

	@NotifyChange({ "currentComments", "captionLabelProject" , "selectedDemand" })
	public void setSelectedDemand(Demand selectedDemand) {
		this.selectedDemand = selectedDemand;
		
		captionLabelProject = selectedDemand.getTitle();
		txtStartDate = selectedDemand.getTitle();
		
		
		
		DecimalFormat df = new DecimalFormat("#");
		df.setMaximumFractionDigits(0);
		
		
		
		currentComments = projectData.allprjcommentsForId(df.format(selectedDemand.getID()));
	}

	public List<Demand> getDemandList() {
		return demandList;
	}

	public void setDemandList(List<Demand> demandList) {
		this.demandList = demandList;
	}

	public List<PrjComment> getPrjCommentList() {
		return prjCommentList;
	}

	public void setPrjCommentList(List<PrjComment> prjCommentList) {
		this.prjCommentList = prjCommentList;
	}

	public List<DemandResources> getDemandResourcesList() {
		return demandResourcesList;
	}

	public void setDemandResourcesList(List<DemandResources> demandResourcesList) {
		this.demandResourcesList = demandResourcesList;
	}

	public ProjectData getProjectData() {
		return projectData;
	}

	public void setProjectData(ProjectData projectData) {
		this.projectData = projectData;
	}

	public String getKeywordForDemand() {
		return keywordForDemand;
	}

	public void setKeywordForDemand(String keywordForDemand) {
		this.keywordForDemand = keywordForDemand;
	}

	public String getCurrentComments() {
		return currentComments;
	}

	public void setCurrentComments(String currentComments) {
		this.currentComments = currentComments;
	}

	public String getNewComment() {
		return newComment;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}

	public List<Resource> getResourceList() {
		return resourceList;
	}

	public void setResourceList(ListModelList<Resource> resourceList) {
		this.resourceList = resourceList;
	}

	public String getCaptionLabelProject() {
		return captionLabelProject;
	}

	public void setCaptionLabelProject(String captionLabelProject) {
		this.captionLabelProject = captionLabelProject;
	}

	public String getTxtStartDate() {
		return txtStartDate;
	}

	public void setTxtStartDate(String txtStartDate) {
		this.txtStartDate = txtStartDate;
	}

	
	
	
	
	

}
