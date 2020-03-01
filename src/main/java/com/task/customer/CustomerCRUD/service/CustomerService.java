package com.task.customer.CustomerCRUD.service;

import java.util.List;

import com.task.customer.CustomerCRUD.entity.Customer;


public interface CustomerService {

	public List<Customer> findAll();
	public Customer findById(long id);
	public void save(Customer theEmployee);
	public void deleteById(long id);
}
