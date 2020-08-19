package com.hyperdrive.woodstock.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.entities.Material;
import com.hyperdrive.woodstock.entities.enums.StockUnit;

public class MaterialDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@NotBlank
	private String name;
	
	private String description;
	
	@NotNull
	@Min(value = 0)
	private Integer stock;
	
	@NotNull
	private StockUnit unit;
	
	@NotNull
	@Min(value = 1)
	private Long companyId;

	public MaterialDTO(String name, String description, Integer stock, StockUnit unit, Long companyId) {
		super();
		this.name = name;
		this.description = description;
		this.stock = stock;
		this.unit = unit;
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

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public StockUnit getUnit() {
		return unit;
	}

	public void setUnit(StockUnit unit) {
		this.unit = unit;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Material toMaterial() {
		Material mat = new Material();
		mat.setName(name);
		mat.setDescription(description);
		mat.setStock(stock);
		mat.setUnit(unit);
		
		Company comp = new Company();
		comp.setId(companyId);
		
		mat.setCompany(comp);
		
		return mat;
	};
	
}
