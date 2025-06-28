package com.aims.son.validators.handlers;

import com.aims.son.utils.Utils;
import com.aims.son.validators.BaseFormValidator;
import com.aims.son.validators.ValidatorResult;

import java.util.HashMap;
import java.util.logging.Logger;

public class RequiredFieldValidatorHandler extends BaseFormValidator {
    private static Logger LOGGER = Utils.getLogger(RequiredFieldValidatorHandler.class.getName());
    private String[] requiredFields;
    public RequiredFieldValidatorHandler(String[] requiredFields) {
        this.requiredFields = requiredFields;
    }

    @Override
    public ValidatorResult validate(HashMap<String, String> form) {
        LOGGER.info("========> Validating Required Fields ...");
        for (String field : requiredFields) {
            if (!form.containsKey(field) || form.get(field) == null || form.get(field).trim().isEmpty()) {
                LOGGER.info("========> Validate Failed");
                return ValidatorResult.error(field.toUpperCase() + " is required");
            }
        }
        LOGGER.info("========> Validate Successful");
        return validateNext(form);
    }
}
