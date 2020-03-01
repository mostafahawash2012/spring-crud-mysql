package com.task.customer.CustomerCRUD.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.task.customer.CustomerCRUD.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
}
