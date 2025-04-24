package com.aims.group19.exception;

public class RejectedTransactionException extends PaymentException {
    public RejectedTransactionException() {
        super("ERROR: GD Hoàn trả bị từ chối");
    }
}
