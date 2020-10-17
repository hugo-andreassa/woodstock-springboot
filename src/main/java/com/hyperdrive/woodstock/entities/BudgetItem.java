package com.hyperdrive.woodstock.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hyperdrive.woodstock.entities.enums.BudgetItemStatus;

/** BudgetItem
 * 
 * @author Hugo A. 
 */
@Entity
@Table(name = "tb_budget_item")
public class BudgetItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String description;
	
	@Column(nullable = false)
	private Double price;
	
	@Column(nullable = false)
	private Integer quantity;
	
	private String environment;
	
	@Column(nullable = false)
	private BudgetItemStatus status;

	@ManyToOne
	@JoinColumn(name = "budget_id", nullable = false)
	private Budget budget;
	
	@OneToMany(mappedBy = "budgetItem", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<CuttingPlan> cuttingPlan;
	
	@OneToMany(mappedBy = "budgetItem", cascade = CascadeType.REMOVE)
	@JsonIgnore
	private List<Project> project;
	
	public BudgetItem() {
		super();
	}
	
	public BudgetItem(Long id, String description, Double price, Integer quantity, String environment,
			BudgetItemStatus status, Budget budget) {
		super();
		this.id = id;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.environment = environment;
		this.status = status;
		this.budget = budget;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public BudgetItemStatus getStatus() {
		return status;
	}

	public void setStatus(BudgetItemStatus status) {
		this.status = status;
	}
	
	@JsonIgnore
	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}
	
	public List<CuttingPlan> getCuttingPlan() {
		return cuttingPlan;
	}

	public List<Project> getProject() {
		return project;
	}
	
	public Double subTotal() {
		return quantity * price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BudgetItem other = (BudgetItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}	
}
