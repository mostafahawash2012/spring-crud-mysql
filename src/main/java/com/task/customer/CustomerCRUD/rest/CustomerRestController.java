package com.task.customer.CustomerCRUD.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

//import com.cegedim.it.common.api.security.mfa.NoMfaRequired;
import com.task.customer.CustomerCRUD.entity.Customer;
import com.task.customer.CustomerCRUD.service.CustomerServiceImpo;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CustomerRestController {

	private CustomerServiceImpo customerService;
	private SimpMessagingTemplate template;
	
	@Autowired
	public CustomerRestController(CustomerServiceImpo theCustomerService,SimpMessagingTemplate t) {
		customerService = theCustomerService;
		template = t;
	}
	
	
	@MessageMapping("/customers")
	@SendTo("/topic/customers")
	public List<Customer> handleWSMessage(@RequestBody String customer) {
		
//		return customer;
		return customerService.findAll();
	}
	
	//@NoMfaRequired
	@PostMapping("/customers")
	@ApiOperation(value = "Create a new customer")
	@ResponseStatus(HttpStatus.CREATED)
	public Customer addCustomer(@RequestBody Customer customer) {
		
		customer.setId(0);
		customerService.save(customer);
		Map<String, Object> data = new HashMap<String,Object>();
		data.put("operation","POST");
		data.put("data", customer);
		template.convertAndSend("/topic/customers", data );
		return customer;
	}
	
	@ApiOperation("get all customers")
	@GetMapping("/customers")
	@ResponseStatus(HttpStatus.OK)
	public List<Customer> getAll(){
		
		return customerService.findAll();
	}
	
	@GetMapping("/customers/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Customer getCustomer(@PathVariable int id) {
		
		Customer customer = customerService.findById(id);
		if(customer == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found - Id "+id);
		
		return customer;
	}
	

	//@NoMfaRequired
	@PutMapping("/customers")
	@ResponseStatus(HttpStatus.OK)
	public Customer updateCustomer(@RequestBody Customer theCustomer) {
		Customer customer = customerService.findById(theCustomer.getId());
		if(customer == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found - Id "+theCustomer.getId());
		customerService.save(theCustomer);
		Map<String, Object> data = new HashMap<String,Object>();
		data.put("operation","PUT");
		data.put("data", customer);
		template.convertAndSend("/topic/customers", data);
		return theCustomer;
	}
	//@NoMfaRequired
	@DeleteMapping("/customers/{id}")
	@ResponseStatus(HttpStatus.OK)
	public String deleteCustomer(@PathVariable int id) {
		
		Customer customer = customerService.findById(id);
		if(customer == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found - Id "+id);
		
		customerService.deleteById(id);
		Map<String, Object> data = new HashMap<String,Object>();
		data.put("operation","DELETE");
		data.put("data", customer);
		template.convertAndSend("/topic/customers", data);
		return "customer deleted - Id: "+id;
	}
}
