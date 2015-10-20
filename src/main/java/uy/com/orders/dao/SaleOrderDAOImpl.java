package uy.com.orders.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import uy.com.orders.model.Customer;
import uy.com.orders.model.LineOrder;
import uy.com.orders.model.Product;
import uy.com.orders.model.SaleOrder;

import javax.transaction.Transactional;

@Repository
public class SaleOrderDAOImpl extends AbstractDAO<SaleOrder> implements SaleOrderDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ProductDAO productDao;
	@Autowired
	private CustomerDAO customerDao;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }    

	@Transactional
	@Override
	public SaleOrder saveOrder(SaleOrder s) {

			BigDecimal total = BigDecimal.ZERO;
			for(LineOrder line:s.getLines()){			
				Product p = productDao.findObject(line.getProduct().getCode());				
				p.setQuantity(p.getQuantity() - line.getQuantity());
				productDao.update(p);
				BigDecimal currentTotal = p.getPrice().multiply(new BigDecimal(line.getQuantity()));
				total = total.add(currentTotal);
				line.setProduct(p);
			}
			Customer c = customerDao.findObject(s.getCustomer().getCode());
			c.setCurrentCredit(c.getCurrentCredit().add(total));
			customerDao.update(c);
			s.setCustomer(c);
			getSession().persist(s);

		return s;
	}

	@Override
	public SaleOrder findObject(String orderNumber) {
		return (SaleOrder) getSession().createQuery("from SaleOrder where orderNumber = :orderNumber ").uniqueResult();
	}


	





}
