package com.aims.group19.exception;

public class InvalidChecksumException extends PaymentException {
    public InvalidChecksumException() {
        super("Mã checksum không hợp lệ!!!");
    }
}
