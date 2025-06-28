package com.aims.son.exception;

public class TransactionFailedException extends PaymentException {

    public TransactionFailedException() {
        super("ERROR: Giao dịch thất bại!");
    }

}
