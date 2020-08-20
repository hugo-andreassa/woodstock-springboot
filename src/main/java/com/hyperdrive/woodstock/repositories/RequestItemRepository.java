package com.hyperdrive.woodstock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperdrive.woodstock.entities.RequestItem;
import com.hyperdrive.woodstock.entities.pk.RequestItemPK;

public interface RequestItemRepository extends JpaRepository<RequestItem, RequestItemPK>{

	
}
