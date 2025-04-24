package com.aims.group19.validators.handlers;

import com.aims.group19.validators.BaseFormValidator;
import com.aims.group19.validators.ValidatorResult;

import java.util.HashMap;

public class DeliveryInstructionFieldValidatorHandler extends BaseFormValidator {

    @Override
    public ValidatorResult validate(HashMap<String, String> form) {
        // Lấy giá trị của trường Delivery Instruction
        String deliveryInstruction = form.get("deliveryInstruction");

        // Kiểm tra null hoặc rỗng
        if (deliveryInstruction == null || deliveryInstruction.trim().isEmpty()) {
            return ValidatorResult.error("Delivery instruction cannot be empty.");
        }

        // Kiểm tra độ dài tối thiểu
        if (deliveryInstruction.length() <= 2) {
            return ValidatorResult.error("Delivery instruction must be longer than 2 characters.");
        }

        return ValidatorResult.success();
    }
}
