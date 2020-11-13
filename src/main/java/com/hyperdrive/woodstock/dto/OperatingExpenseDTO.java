package com.hyperdrive.woodstock.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.entities.OperatingExpense;

/** OperatingExpense DTO
 * 
 * @author Hugo A.
 */
public class OperatingExpenseDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@NotBlank
	private String name;

	private String description;
	
	@NotNull
	@Min(value = 1)
	private Double value;
	
	@NotNull
	@Min(value = 1)
	private Long companyId;
	
	public OperatingExpenseDTO() {
		
	}

	public OperatingExpenseDTO(String name, String description, Double value, Long companyId) {
		super();
		this.name = name;
		this.description = description;
		this.value = value;
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	
	public OperatingExpense toOperatingExpense() {
		OperatingExpense expense = new OperatingExpense();
		expense.setName(name);
		expense.setDescription(description);
		expense.setValue(value);
		
		Company comp = new Company();
		comp.setId(companyId);
		
		expense.setCompany(comp);
		
		return expense;
	}
}
