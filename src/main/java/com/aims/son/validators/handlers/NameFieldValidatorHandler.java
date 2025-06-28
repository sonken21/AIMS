package com.aims.son.validators.handlers;

import com.aims.son.utils.Utils;
import com.aims.son.validators.BaseFormValidator;
import com.aims.son.validators.ValidatorHelpers;
import com.aims.son.validators.ValidatorResult;

import java.util.HashMap;
import java.util.logging.Logger;

public class NameFieldValidatorHandler extends BaseFormValidator {
    private static Logger LOGGER = Utils.getLogger(NameFieldValidatorHandler.class.getName());
    @Override
    public ValidatorResult validate(HashMap<String, String> form) {
        LOGGER.info("========> Validating Name Field ...");
        String name = form.get("name");
        name = name.trim();
        if(name.length() < 2 || name.length() > 50 || !name.matches("^[\\p{L}\\s]+$")){
            LOGGER.info("========> Validate Failed");
            return ValidatorResult.error("Name must be between 2-50 characters and can only contain letters and spaces. Example: Nguyen Van A");
        }
        LOGGER.info("========> Validate Successful");
        return validateNext(form);
    }
}
