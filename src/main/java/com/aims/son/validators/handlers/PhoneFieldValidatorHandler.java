package com.aims.son.validators.handlers;

import com.aims.son.utils.Utils;
import com.aims.son.validators.BaseFormValidator;
import com.aims.son.validators.ValidatorResult;

import java.util.HashMap;
import java.util.logging.Logger;

public class PhoneFieldValidatorHandler extends BaseFormValidator {
    private static Logger LOGGER = Utils.getLogger(PhoneFieldValidatorHandler.class.getName());
    @Override
    public ValidatorResult validate(HashMap<String, String> form) {
        LOGGER.info("========> Validating Phone Field ...");
        String phone = form.get("phone");
        String cleanPhoneNumber = phone.replaceAll("[-. ]", "");
        if(!(cleanPhoneNumber.matches("^0\\d{9}$") &&
                phone.matches("^[0-9\\s\\-.]+$"))){
            LOGGER.info("========> Validate Failed");
            return ValidatorResult.error("Phone number must start with 0 and have 10 digits in total. Spaces, dots and hyphens are allowed. Example: 0912-345-678 or 0912.345.678");
        }
        LOGGER.info("========> Validate Successful");
        return validateNext(form);
    }
}
