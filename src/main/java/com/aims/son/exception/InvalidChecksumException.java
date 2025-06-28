package com.aims.son.exception;

public class InvalidChecksumException extends PaymentException {
    public InvalidChecksumException() {
        super("Mã checksum không hợp lệ!!!");
    }
}
