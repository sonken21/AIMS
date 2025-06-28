package com.aims.son.exception;

public class WrongDataFormatException extends PaymentException {
    public WrongDataFormatException() {
        super("Dữ liệu gửi sang không đúng định dạng!!!");
    }
}
