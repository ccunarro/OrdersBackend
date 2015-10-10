package uy.com.orders.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 *
 * @author carlos
 *
 */
@Entity
@Table(name="CUSTOMER")
public class Customer {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(unique=true)
	private String code;
	
	private String name;
	
	private String phone;
	
	private String phone2;
	
	private String address;
	
	@Column(name = "current_credit", scale=1)
	private BigDecimal currentCredit;
	
	@Column(name = "credit_limit", scale = 1)
	private BigDecimal creditLimit;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
		
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public BigDecimal getCurrentCredit() {
		return currentCredit;
	}

	public void setCurrentCredit(BigDecimal currentCredit) {
		this.currentCredit = currentCredit;
	}	

	public BigDecimal getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(BigDecimal creditLimit) {
		this.creditLimit = creditLimit;
	}
	

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString(){
		return "id="+id +" code="+code+" name="+name+" phone="+phone+" currentCredit="+currentCredit + " creditLimit="+creditLimit;
	}
	

}
