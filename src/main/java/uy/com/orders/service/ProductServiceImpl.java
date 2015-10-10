package uy.com.orders.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import uy.com.orders.dao.ProductDAO;
import uy.com.orders.model.Product;

public class ProductServiceImpl implements ProductService {
	
	@Autowired
	private ProductDAO ProductDao;

	@Override
	public Product saveOrUpdate(Product p) {
		if(ProductDao.findObject(p.getCode()) == null){
			return ProductDao.save(p);			
		}
		else{
			return ProductDao.update(p);
		}
	}

	@Override
	public Product findObject(String code) {		
		return ProductDao.findObject(code);
	}

	@Override
	public void delete(String code) {
		ProductDao.delete(code);		
	}

	@Override
	public List<Product> listObjects() {
		return ProductDao.listObjects();
	}

	public ProductDAO getProductDao() {
		return ProductDao;
	}

	public void setProductDao(ProductDAO ProductDao) {
		this.ProductDao = ProductDao;
	}
	
	

}
