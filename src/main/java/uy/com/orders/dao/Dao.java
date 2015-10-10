package uy.com.orders.dao;

import java.util.List;

public interface Dao<T extends Object> {
	
	public T save(T object);
	
	public T update(T object);
	
	public T findObject(String code);
	
	public void delete(String code);
	
	public List<T> listObjects();

}
