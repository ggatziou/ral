package com.meluna.springbootjdbc.pojos.ral;

import java.sql.Date;

public class Allocation {
	
	Integer allocationId;
	int personId;
	int projectId;
	Date endDate;
	String planviewProjectId;	
	String title;	
	String firstName;
	String lastName;

	
	
	public Integer getAllocationId() {
		return allocationId;
	}
	public void setAllocationId(Integer allocationId) {
		this.allocationId = allocationId;
	}
	public int getPersonId() {
		return personId;
	}
	public void setPersonId(int personId) {
		this.personId = personId;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getPlanviewProjectId() {
		return planviewProjectId;
	}
	public void setPlanviewProjectId(String planviewProjectId) {
		this.planviewProjectId = planviewProjectId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allocationId == null) ? 0 : allocationId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Allocation))
			return false;
		Allocation other = (Allocation) obj;
		if (allocationId == null) {
			if (other.allocationId != null)
				return false;
		} else if (!allocationId.equals(other.allocationId))
			return false;
		return true;
	}
	
	
	
	
	

}
