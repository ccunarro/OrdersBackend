package uy.com.orders.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * 
 *
 * @author carlos
 *
 */
@Entity
@Table(name="SALE_ORDER")
public class SaleOrder {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(unique=true, name = "order_number")
	private String orderNumber;
	
	//@Cascade(value=org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="customer")
	private Customer customer;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="saleorder") 
	private Set<LineOrder> lines;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<LineOrder> getLines() {
		return lines;
	}

	public void setLines(Set<LineOrder> lines) {
		this.lines = lines;
	}

	@Override
	public String toString(){
		return "id="+id;
	}
	

}
