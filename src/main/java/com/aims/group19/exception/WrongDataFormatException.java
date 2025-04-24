package com.aims.group19.exception;

public class WrongDataFormatException extends PaymentException {
    public WrongDataFormatException() {
        super("Dữ liệu gửi sang không đúng định dạng!!!");
    }
}
