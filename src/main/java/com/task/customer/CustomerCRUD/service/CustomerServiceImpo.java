package com.task.customer.CustomerCRUD.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.task.customer.CustomerCRUD.dao.CustomerRepository;
import com.task.customer.CustomerCRUD.entity.Customer;

@Service
public class CustomerServiceImpo implements CustomerService {

	private CustomerRepository repository;
	
	@Autowired
	public CustomerServiceImpo(CustomerRepository customerRepository) {
		repository = customerRepository;
	}
	
	@Override
	public List<Customer> findAll() {
		return repository.findAll();
	}

	@Override
	public Customer findById(long id) {
		Optional<Customer> optionalResult = repository.findById(id);
		Customer customer = null;
		
		if(optionalResult.isPresent())
			customer = optionalResult.get();
		else
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found - Id "+id);
		
		return customer;
	}

	@Override
	public void save(Customer customer) {
		repository.save(customer);
	}

	@Override
	public void deleteById(long id) {
		repository.deleteById(id);
	}

}
