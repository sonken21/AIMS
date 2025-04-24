package com.aims.group19.validators.handlers;

import com.aims.group19.utils.Utils;
import com.aims.group19.validators.BaseFormValidator;
import com.aims.group19.validators.ValidatorResult;

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
