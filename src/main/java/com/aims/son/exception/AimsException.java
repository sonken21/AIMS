package com.aims.son.exception;

/**
 * The AimsException wraps all unchecked exceptions. You can use this
 * exception to inform the user about any issues in the AIMS application.
 *
 * @author nguyenlm
 */
public class AimsException extends RuntimeException {
	private static final long serialVersionUID = 1L;
    public AimsException() {
		super();
	}
	// Constructor nhận thông điệp lỗi
	public AimsException(String message) {
		super(message);
	}
	public AimsException(String message, Throwable cause) {
		super(message, cause);  // Gọi constructor của RuntimeException
	}
}

