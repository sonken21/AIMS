package com.aims.son.exception;

public class NotEnoughBalanceException extends PaymentException{

	public NotEnoughBalanceException() {
		super("ERROR: Not enough balance in card!");
	}

}
