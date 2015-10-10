package uy.com.orders.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uy.com.orders.model.SaleOrder;
import uy.com.orders.service.SaleOrderService;

@RestController
@RequestMapping("/saleorders")
public class SaleOrderRestController {
	@Autowired
	private SaleOrderService saleOrderService;


	
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public SaleOrder getSaleOrder(@PathVariable String code){		
		return saleOrderService.findObject(code);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<SaleOrder> getSaleOrders(){		
		return saleOrderService.listObjects();
	}
	
	//needs refactoring on Exception handling
	//not enough time to polish Exception Handling
	//the idea is to send custom http codes with error code and to get it on frontend
	//it is done but not on the way I would have liked to
	@RequestMapping(value = "/{code}", method = RequestMethod.POST)
	public SaleOrder saveUpdateSaleOrder(@PathVariable String code,@RequestBody SaleOrder s) throws Exception{			
			return saleOrderService.saveOrUpdate(s);		
	}
	@RequestMapping(value = "/{code}", method = RequestMethod.DELETE)
	public void deleteSaleOrder(@PathVariable String code){		
		saleOrderService.delete(code);
	}
	
	

}
