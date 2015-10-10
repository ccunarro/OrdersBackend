package uy.com.orders.dao;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import uy.com.orders.model.Customer;

public class CustomerDAOImpl implements CustomerDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }    

	@Override
	public Customer save(Customer c) {
		
		c.setCurrentCredit(new BigDecimal(0));
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(c);
		tx.commit();
		session.close();	
		if(logger.isDebugEnabled()) {
			  logger.debug("Customer " + c.toString() + " saved succesfully");
		}
		return c;
	}


	@Override
	public Customer update(Customer c) {
		c.setCurrentCredit(c.getCurrentCredit() == null ? new BigDecimal(0):c.getCurrentCredit());
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("update Customer set name = :name, phone = :phone, phone2 = :phone2,"
				+ "address = :address,creditLimit = :creditLimit,currentCredit = :currentCredit" +
				" where code = :code");
				query.setParameter("name", c.getName());
				query.setParameter("phone", c.getPhone());
				query.setParameter("currentCredit", c.getCurrentCredit());
				query.setParameter("code", c.getCode());
				query.setParameter("phone2", c.getPhone2());
				query.setParameter("address", c.getAddress());
				query.setParameter("creditLimit", c.getCreditLimit());
		query.executeUpdate();
		session.close();
		if(logger.isDebugEnabled()) {
			  logger.debug("Customer " + c.toString() + " updated succesfully");
		}
		return c;
		
	}
	
	@Override
	public Customer findObject(String code) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Customer where code = :code ");
		query.setParameter("code", code);
		Customer customer = (Customer)query.uniqueResult();
		session.close();
		if(logger.isDebugEnabled() && customer != null) {
			  logger.debug("Customer " + customer.toString() + " found succesfully");
		}
		return customer;
	}


	@Override
	public void delete(String code) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("delete Customer where code = :code ");
		query.setParameter("code", code);
		query.executeUpdate();
		session.close();
		if(logger.isDebugEnabled()) {
			  logger.debug("Customer with code " + code + " deleted succesfully");
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> listObjects() {
		Session session = this.sessionFactory.openSession();
		List<Customer> customerList = session.createQuery("from Customer").list();
		session.close();
		if(logger.isDebugEnabled()) {
			  logger.debug("Customer list returned succesfully");
		}
		return customerList;
	}

}
