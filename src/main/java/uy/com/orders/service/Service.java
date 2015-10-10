package uy.com.orders.service;

import java.util.List;
import uy.com.orders.model.Customer;

public interface Service<T extends Object> {
	
	public T saveOrUpdate(T object) throws Exception;
	
	public T findObject(String code);
	
	public void delete(String code);
	
	public List<T> listObjects();

}
