package com.aims.son.exception;

/**
 * The MediaNotAvailableException wraps all unchecked exceptions. You can use this
 * exception to inform the user that the requested media is not available.
 *
 * @author nguyenlm
 */
public class MediaNotAvailableException extends AimsException {

	private static final long serialVersionUID = 1091337136123906298L;

	// Constructor không tham số
	public MediaNotAvailableException() {
		super();
	}

	// Constructor nhận thông điệp lỗi
	public MediaNotAvailableException(String message) {
		super(message);
	}
	// Constructor nhận thông điệp lỗi và nguyên nhân (Throwable)
	public MediaNotAvailableException(String message, Throwable cause) {
		super(message, cause);  // Gọi constructor của AimsException
	}

}

