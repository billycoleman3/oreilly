package com.orielly.invoice.dao.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.orielly.invoice.dao.InvoiceDao;
import com.orielly.invoice.model.Invoice;

/**
 * Implementation of {@link InvoiceDao}
 * 
 * @author Billy Coleman
 */

@Service
public class InvoiceDaoImpl implements InvoiceDao {
	
	Logger logger = LoggerFactory.getLogger(InvoiceDaoImpl.class);
	
	private static String fileDelimiter = "~";
	
	@Autowired
	private ResourceLoader resourceLoader;

	@Override
	public List<Invoice> getInvoicesByCustomerId(long customerId) {
		logger.debug("Entering getInvoicesByCustomerId. customerId = " + customerId);
		
		List<Invoice> invoices = new ArrayList<Invoice>();
		invoices = readInvoices(customerId);
		
		logger.debug("Exiting getInvoicesByCustomerId");
		return invoices;
	}
	
	/**
	 * Helper method to mock reading the ORIELLYPOS database
	 * @param customerId the Customer Id
	 * @return {@link List} of matching {@link Invoice}</br>
	 * 
	 * {@link NumberFormatException} is caught and handled by logging the
	 * error.  This allows the processing to continue iterating the data
	 * instead of stopping the process completely</br>
	 * 
	 * {@link Exception} is used to catch any Exception and log the error.
	 * The returned {@link List} will not be never be null (as it is instantiated)
	 * if an exception is encountered and can be checked in the calling service for 
	 * the presence of values
	 */
	private List<Invoice> readInvoices(long customerId) {
		logger.debug("Entering readInvoices. id = " + customerId);
		
		List<Invoice> invoices = new ArrayList<Invoice>();

		Resource resource = resourceLoader.getResource("classpath:oriellypos.txt");

		try {
			File file = resource.getFile();

			try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {

				String line;
				while ((line = bufferedReader.readLine()) != null) {
					String[] data = line.split(fileDelimiter);
					if(Long.valueOf(data[0]) == customerId) {
						Invoice newInvoice = new Invoice(Long.valueOf(data[0]), 
								Long.valueOf(data[1]), data[2]);
						invoices.add(newInvoice);
					}
				}
			}
			catch(NumberFormatException nfe) {
				logger.error("Invalid Long data encountered: " + nfe.getLocalizedMessage());
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		} 

		logger.debug("Exiting readInvoices");
		return invoices;
	}
}
