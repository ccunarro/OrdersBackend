package uy.com.orders.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.PRECONDITION_FAILED, reason="No Credit Limit Available")
public class CreditLimitBusinessException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3456846920552166120L;



}
