package uy.com.orders.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import uy.com.orders.model.Product;

public class ProductDAOImpl implements ProductDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }    

	@Override
	public Product save(Product p) {
		
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.persist(p);
		tx.commit();
		session.close();	
		return p;
	}


	@Override
	public Product update(Product p) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("update Product set code = :code, description = :description, quantity = :quantity,"
				+ "price = :price where code = :code");
				query.setParameter("code", p.getCode());
				query.setParameter("description", p.getDescription());
				query.setParameter("quantity", p.getQuantity());
				query.setParameter("price", p.getPrice());
		query.executeUpdate();
		return p;
		
	}
	
	@Override
	public Product findObject(String code) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("from Product where code = :code ");
		query.setParameter("code", code);
		Product Product = (Product)query.uniqueResult();
		session.close();	
		return Product;
	}


	@Override
	public void delete(String code) {
		Session session = this.sessionFactory.openSession();
		Query query = session.createQuery("delete Product where code = :code ");
		query.setParameter("code", code);
		query.executeUpdate();
		session.close();
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Product> listObjects() {
		Session session = this.sessionFactory.openSession();
		List<Product> ProductList = session.createQuery("from Product").list();
		session.close();
		return ProductList;
	}

}
