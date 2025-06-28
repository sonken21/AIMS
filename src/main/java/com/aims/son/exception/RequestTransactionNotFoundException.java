package com.aims.son.exception;

public class RequestTransactionNotFoundException extends PaymentException {
    public RequestTransactionNotFoundException() {
        super("Không tìm thấy giao dịch yêu cầu!!!");
    }
}
