package com.hyperdrive.woodstock.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.hyperdrive.woodstock.entities.enums.StockUnit;

/** Material
 * 
 * @author Hugo A.
 */
@Entity
@Table(name = "tb_material")
public class Material implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	private String description;
	
	@Column(nullable = false)
	private Integer stock;
	
	@Column(nullable = false)
	private Integer minimumStock;
	
	@Column(nullable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", locale = "pt-BR", timezone = "America/Sao_Paulo")
	@JsonProperty(access = Access.READ_ONLY)
	private Instant lastUpdate;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private StockUnit unit;
	
	@ManyToOne
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;
	
	@OneToMany(mappedBy = "id.material")
	private Set<RequestItem> items = new HashSet<>();
	
	public Material() {
		
	}

	public Material(Long id, String name, String description, Integer stock, Integer minimumStock, Instant lastUpdate,
			StockUnit unit, Company company) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.stock = stock;
		this.minimumStock = minimumStock;
		this.lastUpdate = lastUpdate;
		this.unit = unit;
		this.company = company;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
	
	public Integer getMinimumStock() {
		return minimumStock;
	}

	public void setMinimumStock(Integer minimumStock) {
		this.minimumStock = minimumStock;
	}

	public Instant getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Instant lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public StockUnit getUnit() {
		return unit;
	}

	public void setUnit(StockUnit unit) {
		this.unit = unit;
	}

	@JsonIgnore
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@JsonIgnore
	public Set<Request> getItems() {
		Set<Request> set = new HashSet<>();
		
		for (RequestItem item : items) {
			set.add(item.getRequest());
		}
		
		return set;
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
		Material other = (Material) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
