package com.aims.son.entity.payment;

import java.util.Date;

public class PaymentTransaction {
	private final String errorCode;
	private final String transactionId;
	private final String transactionContent;
	private final long amount;
	private Integer orderID;
	private final Date createdAt;

	public PaymentTransaction(String errorCode, String transactionId, String transactionContent,
							  long amount, Integer orderID, Date createdAt) {
		super();
		this.errorCode = errorCode;
		this.transactionId = transactionId;
		this.transactionContent = transactionContent;
		this.amount = amount;
		this.orderID = orderID;
		this.createdAt = createdAt;
	}

	public boolean isSuccess() {
		return errorCode == null || "00".equals(errorCode);
	}

	public String getMessage() {
		return isSuccess() ? "Payment was successful." : "Payment failed with error code: " + errorCode;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public String getTransactionContent() {
		return transactionContent;
	}

	public long getAmount() {
		return amount;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID){
		this.orderID = orderID;
	}
}

