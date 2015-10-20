package uy.com.orders.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import uy.com.orders.model.Product;
@Repository
public class ProductDAOImpl extends AbstractDAO<Product> implements ProductDAO {


}
