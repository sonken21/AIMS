package com.aims.son.exception;

public class PlaceOrderException extends Exception {

	// Constructor mặc định
	public PlaceOrderException() {
		super();
	}

	// Constructor nhận thông điệp lỗi
	public PlaceOrderException(String message) {
		super(message);
	}

	// Constructor nhận thông điệp lỗi và nguyên nhân (Exception)
	public PlaceOrderException(String message, Throwable cause) {
		super(message, cause);
	}
}
