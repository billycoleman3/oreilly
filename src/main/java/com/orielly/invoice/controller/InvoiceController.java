package com.orielly.invoice.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.orielly.invoice.model.Invoice;
import com.orielly.invoice.service.InvoiceService;

/**
 * Controller to handle HTTP {@link Invoice} requests
 * 
 * @author Billy Coleman
 */

@RestController
public class InvoiceController {
	
	Logger logger = LoggerFactory.getLogger(InvoiceController.class);
	
	@Autowired
	InvoiceService service;

	/**
	 * RESTful service to retrieve a {@link Map} of Invoice data
	 * 
	 * @param customerId the Customer ID to retrieve data for
	 * @return {@link Map} the map containing the data </br>
	 * 
	 * Key = Invoice Number </br>
	 * Value = Tender Type
	 */
	@GetMapping("/invoices/{customerId}")
	public Map<Long, String> getInvoicesByCusomertId(@PathVariable("customerId") 
		long customerId) {
		
		logger.debug("Entering getInvoicesByCusomertId");
		
		return service.getInvoicesByCustomerId(customerId);
	}
}
 