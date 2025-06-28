package com.aims.son.exception;

public class InvalidConnectionIdentifierException extends PaymentException {
    public InvalidConnectionIdentifierException() {
        super("Mã định danh kết nối không hợp lệ!!!");
    }
}
