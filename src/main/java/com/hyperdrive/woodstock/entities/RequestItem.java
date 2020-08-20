package com.hyperdrive.woodstock.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hyperdrive.woodstock.entities.pk.RequestItemPK;

/** RequestItem
 * 
 * @author Hugo A.
 */
@Entity
@Table(name = "tb_request_item")
public class RequestItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private RequestItemPK id = new RequestItemPK();
	
	@Column(nullable = false)
	private Integer quantity;
	
	private Double price;
	
	public RequestItem() {
		
	}

	public RequestItem(Request request, Material material, Integer quantity, Double price) {
		id.setRequest(request);
		id.setMaterial(material);
		this.quantity = quantity;
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@JsonIgnore
	public Request getRequest() {
		return id.getRequest();
	}

	public void setRequest(Request request) {
		id.setRequest(request);
	}
	
	public Material getMaterial() {
		return id.getMaterial();
	}

	public void setMaterial(Material material) {
		id.setMaterial(material);
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
		RequestItem other = (RequestItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
