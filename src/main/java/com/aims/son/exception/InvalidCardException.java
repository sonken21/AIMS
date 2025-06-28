package com.aims.son.exception;

public class InvalidCardException extends PaymentException {
	public InvalidCardException() {
		super("ERROR: Invalid card!");
	}
}
