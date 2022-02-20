package com.meluna.springbootjdbc.zk.vm;

import java.util.List;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zul.ListModelList;

import com.meluna.springbootjdbc.pojos.ral.Allocation;
import com.meluna.springbootjdbc.zk.model.AllocationData;

public class AllocationViewModel {
	
	private String keyword;
	private List<Allocation> allocationList = new ListModelList<Allocation>();
	private Allocation selectedAllocation;
	
	private AllocationData allocationData = new AllocationData(); 
	
	
	
	@Command
	public void search(){
		allocationList.clear();
		allocationList.addAll(allocationData.search(keyword));
	}
	
	public List<Allocation> getAllocationList(){
		return allocationList;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Allocation getSelectedAllocation() {
		return selectedAllocation;
	}

	public void setSelectedAllocation(Allocation selectedAllocation) {
		this.selectedAllocation = selectedAllocation;
	}

	
	
}
