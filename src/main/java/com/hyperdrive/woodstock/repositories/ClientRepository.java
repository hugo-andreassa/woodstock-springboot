package com.hyperdrive.woodstock.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hyperdrive.woodstock.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{

}
