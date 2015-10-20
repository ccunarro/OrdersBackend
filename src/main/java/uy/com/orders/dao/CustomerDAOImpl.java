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

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import uy.com.orders.model.Customer;

@Repository
public class CustomerDAOImpl extends AbstractDAO<Customer> implements CustomerDAO {
	


}
