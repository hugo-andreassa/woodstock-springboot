package com.hyperdrive.woodstock.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hyperdrive.woodstock.entities.Material;
import com.hyperdrive.woodstock.entities.Request;
import com.hyperdrive.woodstock.entities.RequestItem;

public class RequestItemDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotNull
	@Min(value = 1)
	private Integer quantity;
	
	private Double price;
	
	@NotNull
	@Min(value = 1)
	private Long requestId;
	
	@NotNull
	@Min(value = 1)
	private Long materialId;

	public RequestItemDTO() {
		super();
	}

	public RequestItemDTO(Integer quantity, Double price, Long requestId, Long materialId) {
		super();
		this.quantity = quantity;
		this.price = price;
		this.requestId = requestId;
		this.materialId = materialId;
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

	public Long getRequestId() {
		return requestId;
	}

	public void setRequestId(Long requestId) {
		this.requestId = requestId;
	}

	public Long getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Long materialId) {
		this.materialId = materialId;
	}
	
	public RequestItem toRequestItem() {
		RequestItem item = new RequestItem();
		item.setQuantity(quantity);
		item.setPrice(price);
		
		Request request = new Request();
		request.setId(requestId);
		item.setRequest(request);
		
		Material material = new Material();
		material.setId(materialId);
		item.setMaterial(material);		
		
		return item;
	}
}
