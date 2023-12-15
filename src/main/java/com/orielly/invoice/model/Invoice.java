package com.orielly.invoice.model;

import java.io.Serializable;

/**
 * Model representing an Invoice
 * 
 * @author Billy Coleman
 */
public class Invoice implements Serializable {
	
	private static final long serialVersionUID = 1604688742659926031L;

	public Invoice() {}
	
	public Invoice(long customerId, long invoiceId, String payload) {
		super();
		this.customerId = customerId;
		this.invoiceId = invoiceId;
		this.payload = payload;
	}

	private long customerId;
	
	private long invoiceId;
	
	private String payload;
	
	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long id) {
		this.customerId = id;
	}

	public long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("\ncustomerId = " + this.getCustomerId() + "\n");
		sb.append("InvoiceId = " + this.getInvoiceId() + "\n");
		sb.append("payload = " + this.getPayload());
		return sb.toString();
		
		 
	}
}
