package com.aims.son.exception;

/**
 * The InvalidDeliveryInfoException wraps all unchecked exceptions. You can use this
 * exception to inform the user about invalid delivery information.
 *
 * @author nguyenlm
 */
public class InvalidDeliveryInfoException extends AimsException {

	private static final long serialVersionUID = 1091337136123906298L;

	// Constructor không tham số
	public InvalidDeliveryInfoException() {
		super();
	}

	// Constructor nhận thông điệp lỗi
	public InvalidDeliveryInfoException(String message) {
		super(message);
	}

	// Constructor nhận thông điệp lỗi và nguyên nhân (Throwable)
	public InvalidDeliveryInfoException(String message, Throwable cause) {
		super(message, cause);
	}

}

