package com.orielly.invoice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.orielly.invoice.dao.InvoiceDao;
import com.orielly.invoice.model.Invoice;
import com.orielly.invoice.service.InvoiceService;

/**
 * Implementation of {@link InvoiceService}
 * 
 * @author Billy Coleman
 */

@Service
public class InvoiceServiceImpl implements InvoiceService {
	
	Logger logger = LoggerFactory.getLogger(InvoiceServiceImpl.class);
	
	public static final String tenderDetailsName = "tenderDetails";
	public static final String typeName = "type";
	
	@Autowired
	public InvoiceDao dao;

	@Override
	public Map<Long, String> getInvoicesByCustomerId(long customerId) {
		logger.debug("Entering getInvoicesByCustomerId. customerId = " + customerId);
		
		Map<Long, String> map;
		
		List<Invoice> invoices = dao.getInvoicesByCustomerId(customerId);
		if(!invoices.isEmpty()) {
			map = buildReturnMap(invoices);
		}
		else {
			map = buildNotFoundMap();
		}
	
		logger.debug("Exiting getInvoicesByCustomerId");
		return map;
	}

	/**
	 * Helper method to build a Map containing valid values
	 * @param invoices a {@link List} of {@link Invoice} objects
	 * @return {@link Map} the map of valid Invoice objects
	 */
	private Map<Long, String> buildReturnMap(List<Invoice> invoices) {
		logger.debug("Entering buildReturnMap.");
		
		Map<Long, String> map = new HashMap<Long,String>();
		
		for(Invoice invoice : invoices) {

			String[] values = getValues(invoice);
			map.put(Long.valueOf(values[0]), values[1]);
		}
		
		logger.debug("Exiting buildReturnMap");
		return map;
	}
	
	/**
	 * Helper method to build a Map representing the absence of a matching
	 * {@link Invoice} for passed in Customer Id
	 * @return {@link Map} containing {@link HttpStatus} codes
	 */
	private Map<Long, String> buildNotFoundMap() {
		logger.debug("Entering buildNotFoundMap");
		
		Map<Long, String> map = new HashMap<Long,String>();
		
		map.put(Long.valueOf(HttpStatus.NOT_FOUND.value()), 
				HttpStatus.NOT_FOUND.name());
		
		logger.debug("Exiting buildNotFoundMap");
		return map;
	}
	
	
	/**
	 * Helper method to build a {@link String[]} containing 
	 * the Invoice ID and the tender type
	 * @param invoice the {@link Invoice} to extract data from
	 * @return {@link String[]} the array of values</br>
	 * 
	 * Catching {@link ParseException} and logging the error. The
	 * value of the tender type will indicate Error.
	 */
	private String[] getValues(Invoice invoice) {
		logger.debug("Entering getValues.");
		
		String[] values = new String[2];
		
		values[0] = String.valueOf(invoice.getInvoiceId());
		
		String payload = invoice.getPayload();
		
		try {
			
			Object obj = new JSONParser().parse(payload);
			JSONObject jsonObject = (JSONObject) obj;
			JSONObject tenderDetails = (JSONObject)jsonObject.get(tenderDetailsName);
			values[1] = (String)tenderDetails.get(typeName);
			
		} catch (ParseException e) {
			logger.error("Error parsing json: " + e.getLocalizedMessage());
			values[1] = "Error";
		}
		
		logger.debug("Exiting getValues");
		return values;

	}
}
