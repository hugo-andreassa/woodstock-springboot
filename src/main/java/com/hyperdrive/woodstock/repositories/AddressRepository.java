package com.hyperdrive.woodstock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperdrive.woodstock.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{

}
