package uy.com.orders.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uy.com.orders.model.Customer;

import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.List;


public abstract class AbstractDAO<T extends Object> implements Dao<T> {
	
	@Autowired
	private SessionFactory sessionFactory;

	private Class<T> domainClass;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	protected Session getSession(){
		return sessionFactory.getCurrentSession();
	}

	@Override
	public T save(T object) {
		getSession().save(object);
		return object;
	}

	@Override
	public T update(T object) {
		getSession().update(object);
		return object;
	}

	@Override
	public T findObject(String code) {
		return (T) getSession().createQuery("from " + getDomainClassName() + " where code = :code ").uniqueResult();
	}

	private String getDomainClassName() {
		return getDomainClass().getName();
	}

	private Class<T> getDomainClass() {
		if (domainClass == null){
			ParameterizedType thisType = (ParameterizedType)getClass().getGenericSuperclass();
			this.domainClass = (Class<T>)thisType.getActualTypeArguments()[0];
		}
		return domainClass;
	}

	@Override
	public void delete(String code) {
		getSession().delete(findObject(code));
	}

	@Override
	public List<T> listObjects() {
		return getSession().createQuery("from "+getDomainClassName()).list();
	}
}
