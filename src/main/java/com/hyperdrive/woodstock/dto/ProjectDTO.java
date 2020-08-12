package com.hyperdrive.woodstock.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hyperdrive.woodstock.entities.BudgetItem;
import com.hyperdrive.woodstock.entities.Project;

public class ProjectDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank
	private String url;
	
	private String comment;
	
	@NotNull
	@Min(value = 1)
	private Long budgetItemId;
	
	public ProjectDTO() {
		
	}

	public ProjectDTO(String url, String comment, Long budgetItemId) {
		super();
		this.url = url;
		this.comment = comment;
		this.budgetItemId = budgetItemId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getBudgetItemId() {
		return budgetItemId;
	}

	public void setBudgetItemId(Long budgetItemId) {
		this.budgetItemId = budgetItemId;
	}
	
	public Project toProject() {
		Project proj = new Project();
		proj.setUrl(url);
		proj.setComment(comment);
		
		BudgetItem bdItem = new BudgetItem();
		bdItem.setId(budgetItemId);
		proj.setBudgetItem(bdItem);
		
		return proj;
	}
	
}
