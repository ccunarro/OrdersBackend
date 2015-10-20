package uy.com.orders.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.*;
import org.springframework.stereotype.Service;
import uy.com.orders.dao.CustomerDAO;
import uy.com.orders.model.Customer;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerDAO customerDao;

	@Override
	public Customer saveOrUpdate(Customer c) {
		if(customerDao.findObject(c.getCode()) == null){
			return customerDao.save(c);			
		}
		else{
			return customerDao.update(c);
		}
	}

	@Override
	public Customer findObject(String code) {		
		return customerDao.findObject(code);
	}

	@Override
	public void delete(String code) {
		customerDao.delete(code);		
	}

	@Override
	public List<Customer> listObjects() {
		return customerDao.listObjects();
	}

	public void setCustomerDao(CustomerDAO customerDao) {
		this.customerDao = customerDao;
	}
	
	

}
