package com.oreilly.invoice.service;

import java.util.Map;

import com.oreilly.invoice.model.Invoice;

/**
 * Provides services for interacting with {@link Invoice}
 * 
 * @author Billy Coleman
 *
 */
public interface InvoiceService {
	
	/**
	 * Retrieve a {@link Map} of {@link Invoice} objects
	 * matching a Customer ID value
	 * @param id the Customer ID
	 * @return {@link Map} of Invoice data
	 */
	Map<Long, String> getInvoicesByCustomerId(long id);

}
