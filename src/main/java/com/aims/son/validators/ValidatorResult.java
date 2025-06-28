package com.aims.son.validators;

public class ValidatorResult {
    private final boolean isSuccess;
    private final String errorMessage;

    private ValidatorResult(boolean isSuccess, String errorMessage) {
        this.isSuccess = isSuccess;
        this.errorMessage = errorMessage;
    }

    // Phương thức factory để tạo kết quả thành công
    public static ValidatorResult success() {
        return new ValidatorResult(true, null);
    }

    // Phương thức factory để tạo kết quả lỗi
    public static ValidatorResult error(String errorMessage) {
        return new ValidatorResult(false, errorMessage);
    }

    // Trả về trạng thái thành công
    public boolean isSuccess() {
        return isSuccess;
    }

    // Trả về thông báo lỗi
    public String getErrorMessage() {
        return errorMessage;
    }
}
