package com.hyperdrive.woodstock.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.hyperdrive.woodstock.entities.Company;
import com.hyperdrive.woodstock.entities.Request;
import com.hyperdrive.woodstock.entities.RequestItem;
import com.hyperdrive.woodstock.entities.enums.RequestStatus;

public class RequestDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String description;
		
	@NotNull
	private RequestStatus status;
	
	@NotNull
	@Min(value = 1)
	private Long companyId;
	
	@NotNull
	private  Set<RequestItemDTO> items = new HashSet<>();
	
	public RequestDTO() {
		
	}

	public RequestDTO(String description, @NotNull RequestStatus status) {
		super();
		this.description = description;
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RequestStatus getStatus() {
		return status;
	}

	public void setStatus(RequestStatus status) {
		this.status = status;
	}
	
	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Set<RequestItemDTO> getItems() {
		return items;
	}
	
	public void setItems(Set<RequestItemDTO> items) {
		this.items = items;
	}

	public Request toRequest() {
		Request request = new Request();
		request.setDescription(description);
		request.setStatus(status);
		
		Set<RequestItem> items = new HashSet<>(); 
		for (RequestItemDTO itemDTO : this.items) {
			RequestItem item = itemDTO.toRequestItem();
			items.add(item);
		}
		request.setItems(items);
		
		Company comp = new Company();
		comp.setId(companyId);
		request.setCompany(comp);		
		
		return request;
	}	
	
}
