package com.aims.group19.exception;

public class DuplicateRequestException extends PaymentException {
    public DuplicateRequestException() {
        super("Yêu cầu request bị trung lặp trong thời gian giới hạn của API!!!");
    }
}
