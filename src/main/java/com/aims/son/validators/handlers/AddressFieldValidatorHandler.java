package com.aims.son.validators.handlers;

import com.aims.son.utils.Utils;
import com.aims.son.validators.BaseFormValidator;
import com.aims.son.validators.ValidatorResult;

import java.util.HashMap;
import java.util.logging.Logger;

public class AddressFieldValidatorHandler extends BaseFormValidator {
    private static Logger LOGGER = Utils.getLogger(AddressFieldValidatorHandler.class.getName());
    @Override
    public ValidatorResult validate(HashMap<String, String> form) {
        LOGGER.info("========> Validating Address Field ...");
        String address = form.get("address");
        address = address.trim();

        if (address.length() < 10) {
            LOGGER.info("========> Validate Failed: Address Too Short");
            return ValidatorResult.error("Address must be at least 10 characters long. Please provide more details");
        }

        if (address.length() > 100) {
            LOGGER.info("========> Validate Failed: Address Too Long");
            return ValidatorResult.error("Address cannot exceed 100 characters. Please make it shorter");
        }

        if (!address.matches("^[\\p{L}0-9\\s,.-]+$")) {
            LOGGER.info("========> Validate Failed: Invalid Characters");
            return ValidatorResult.error("Address can only contain letters, numbers, spaces, commas, dots and hyphens.");
        }
        LOGGER.info("========> Validate Successful");
        return validateNext(form);
    }
}
