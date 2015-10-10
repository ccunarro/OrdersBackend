package uy.com.orders.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import uy.com.orders.model.Customer;
import uy.com.orders.model.LineOrder;
import uy.com.orders.model.Product;
import uy.com.orders.model.SaleOrder;

public class SaleOrderDAOImpl implements SaleOrderDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ProductDAO productDao;
	@Autowired
	private CustomerDAO customerDao;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }    

	@Override
	public SaleOrder save(SaleOrder s) {
		Session session = null;
		Transaction tx = null;

		try {
			session = this.sessionFactory.openSession();			
			tx = session.beginTransaction();				
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
			session.persist(s);
			tx.commit();			
			
		} catch (RuntimeException e) {
			try {
				tx.rollback();
			} catch (RuntimeException rbe) {
				rbe.printStackTrace();
			}
			throw e;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return s;
	}


	@Override
	public SaleOrder update(SaleOrder s) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("update SaleOrder set customer = :customer "
				+ "where orderNumber = :orderNumber");
				query.setParameter("orderNumber", s.getOrderNumber());
				query.setParameter("customer", s.getCustomer());

		query.executeUpdate();
		return s;
		
	}
	
	@Override
	public SaleOrder findObject(String code) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from SaleOrder where order_number = :orderNumber ");
		query.setParameter("orderNumber", code);
		SaleOrder SaleOrder = (SaleOrder)query.uniqueResult();
		session.close();	
		return SaleOrder;
	}


	@Override
	public void delete(String code) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("delete SaleOrder where order_number = :orderNumber ");
		query.setParameter("orderNumber", code);
		query.executeUpdate();
		session.close();
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<SaleOrder> listObjects() {
		Session session = this.sessionFactory.openSession();
		List<SaleOrder> saleOrderList = session.createQuery("from SaleOrder").list();
		session.close();
		return saleOrderList;
	}

}
