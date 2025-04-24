package com.aims.group19.exception;

public class RequestTransactionNotFoundException extends PaymentException {
    public RequestTransactionNotFoundException() {
        super("Không tìm thấy giao dịch yêu cầu!!!");
    }
}
