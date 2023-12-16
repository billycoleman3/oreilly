package com.oreilly.invoice.dao;

import java.util.List;

import com.oreilly.invoice.model.Invoice;

/**
 * Provides data access methods for {@link Invoice}
 * 
 * @author Billy Coleman
 */
public interface InvoiceDao {
	
	/**
	 * Retrieve a {@link List} of {@link Invoice} by Customer Id
	 * @param id the Customer Id
	 * @return {@link List} of matching Invoices
	 */
	List<Invoice> getInvoicesByCustomerId(long id);
	

}
