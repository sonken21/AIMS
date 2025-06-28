package com.aims.son.validators.handlers;

import com.aims.son.utils.Utils;
import com.aims.son.validators.BaseFormValidator;
import com.aims.son.validators.ValidatorResult;

import java.util.HashMap;
import java.util.logging.Logger;

public class EmailFieldValidatorHandler extends BaseFormValidator {
    private static Logger LOGGER = Utils.getLogger(EmailFieldValidatorHandler.class.getName());
    @Override
    public ValidatorResult validate(HashMap<String, String> form) {
        LOGGER.info("========> Validating Email Field ...");
        String email = form.get("email");
        if(!email.matches("^[A-Za-z0-9+_.-]+@(.+)\\.[A-Za-z]{2,}$")){
            LOGGER.info("========> Validate Failed");
            return ValidatorResult.error("Email address can only contain letters, numbers, and common symbols (. _ - +). " +
                    "Example: user.name@domain.com");
        }
        LOGGER.info("========> Validate Successful");
        return validateNext(form);
    }
}
