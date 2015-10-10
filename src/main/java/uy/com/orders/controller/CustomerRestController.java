package uy.com.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uy.com.orders.model.Customer;
import uy.com.orders.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerRestController {
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public Customer getCustomer(@PathVariable String code){		
		return customerService.findObject(code);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Customer> getCustomers(){		
		return customerService.listObjects();
	}
	
	@RequestMapping(value = "/{code}", method = RequestMethod.POST)
	public Customer saveUpdateCustomer(@PathVariable String code,@RequestBody Customer c) throws Exception{		
		return customerService.saveOrUpdate(c);
	}
	@RequestMapping(value = "/{code}", method = RequestMethod.DELETE)
	public void deleteCustomer(@PathVariable String code){		
		customerService.delete(code);
	}

}
