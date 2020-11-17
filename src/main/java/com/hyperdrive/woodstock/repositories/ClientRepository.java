package com.hyperdrive.woodstock.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperdrive.woodstock.entities.Client;
import com.hyperdrive.woodstock.entities.Company;

public interface ClientRepository extends JpaRepository<Client, Long>{

	List<Client> findByCompanyOrderByNameDesc(Company company);
}
