package uy.com.orders.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import uy.com.orders.dao.CustomerDAO;
import uy.com.orders.dao.ProductDAO;
import uy.com.orders.dao.SaleOrderDAO;
import uy.com.orders.model.Customer;
import uy.com.orders.model.LineOrder;
import uy.com.orders.model.Product;
import uy.com.orders.model.SaleOrder;

public class SaleOrderServiceImpl implements SaleOrderService {
	
	@Autowired
	private SaleOrderDAO saleOrderDao;
	@Autowired
	private ProductDAO productDao;
	@Autowired
	private CustomerDAO customerDao;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public SaleOrder saveOrUpdate(SaleOrder s) throws Exception{
		
		if(stockAvailable(s) && creditLimitIsOk(s)){		
			if(saleOrderDao.findObject(s.getOrderNumber()) == null){
				return saleOrderDao.save(s);			
			}
			else{
				return saleOrderDao.update(s);
			}
		}
		
		return s;
		
		
	}

	private boolean creditLimitIsOk(SaleOrder s) throws CreditLimitBusinessException {
		boolean result = true;
		HashMap<Product,Integer> quantities = this.getHashMapOfProducts(s);
		Iterator<?> it = quantities.entrySet().iterator();
		BigDecimal total = BigDecimal.ZERO;
		while(it.hasNext()){
			HashMap.Entry<Product,Integer> pair = (HashMap.Entry<Product,Integer>)it.next();
			Product currentProduct = productDao.findObject(pair.getKey().getCode());
			BigDecimal currentQuantity = new BigDecimal(pair.getValue());
			BigDecimal currentTotal = currentProduct.getPrice().multiply(currentQuantity);
			total = total.add(currentTotal);			
		}
		Customer c = customerDao.findObject(s.getCustomer().getCode());
		BigDecimal currentLimit = c.getCreditLimit().subtract(c.getCurrentCredit());
		
		if(currentLimit.compareTo(total) == -1){
			result=false;
			if(logger.isDebugEnabled()) {
				logger.debug("Credit limit exceeded for customer " + c.toString());
			}
			throw new CreditLimitBusinessException();
		}
		
		return result;
	}

	private boolean stockAvailable(SaleOrder s) throws StockBusinessException {
		boolean result = true;
		HashMap<Product,Integer> quantities = this.getHashMapOfProducts(s);
		Iterator<?> it = quantities.entrySet().iterator();
		while(it.hasNext()){
			HashMap.Entry<Product,Integer> pair = (HashMap.Entry)it.next();
			Product currentProduct = (Product)pair.getKey();
			
			int currentStock = productDao.findObject(currentProduct.getCode()).getQuantity();
			if(currentStock < pair.getValue()){
				result = false;
				if(logger.isDebugEnabled()) {
					logger.debug("No stock available for product with code " + currentProduct.getCode());
				}
				throw new StockBusinessException();
			}
		}
		return result;
	}
	
	private HashMap<Product,Integer> getHashMapOfProducts(SaleOrder s){
		
		Set<LineOrder> list = s.getLines();
		HashMap<Product,Integer> quantities = new HashMap<Product,Integer>();
		
		//this hashmap is to prevent a bad check of stock in case they repeat product in different lines
		//of sale order
		for(LineOrder line: list){		
			Integer current = quantities.get(line.getProduct()) == null ? new Integer(0) : quantities.get(line.getProduct());
			quantities.put(line.getProduct(), current + line.getQuantity());
		}
		return quantities;
	}

	@Override
	public SaleOrder findObject(String code) {		
		return saleOrderDao.findObject(code);
	}

	@Override
	public void delete(String code) {
		saleOrderDao.delete(code);		
	}

	@Override
	public List<SaleOrder> listObjects() {
		return saleOrderDao.listObjects();
	}

	public SaleOrderDAO getSaleOrderDao() {
		return saleOrderDao;
	}

	public void setSaleOrderDao(SaleOrderDAO SaleOrderDao) {
		this.saleOrderDao = SaleOrderDao;
	}

	public ProductDAO getProductDao() {
		return productDao;
	}

	public void setProductDao(ProductDAO productDao) {
		this.productDao = productDao;
	}
	
	
	

}
