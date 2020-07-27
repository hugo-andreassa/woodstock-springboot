package com.hyperdrive.woodstock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperdrive.woodstock.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>{

}
