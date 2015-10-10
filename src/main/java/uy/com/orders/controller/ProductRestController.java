package uy.com.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uy.com.orders.model.Product;
import uy.com.orders.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductRestController {
	@Autowired
	private ProductService productService;


	
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public Product getProduct(@PathVariable String code){		
		return productService.findObject(code);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Product> getProducts(){		
		return productService.listObjects();
	}
	
	@RequestMapping(value = "/{code}", method = RequestMethod.POST)
	public Product saveUpdateProduct(@PathVariable String code,@RequestBody Product p) throws Exception{		
		return productService.saveOrUpdate(p);
	}
	@RequestMapping(value = "/{code}", method = RequestMethod.DELETE)
	public void deleteProduct(@PathVariable String code){		
		productService.delete(code);
	}
	
	

}
