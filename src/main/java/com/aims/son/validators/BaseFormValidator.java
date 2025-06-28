package com.aims.son.validators;

import com.aims.son.utils.Utils;

import java.util.HashMap;
import java.util.logging.Logger;

public abstract class BaseFormValidator {
    protected BaseFormValidator nextValidator;
    private static Logger LOGGER = Utils.getLogger(BaseFormValidator.class.getName());

    public BaseFormValidator setNext(BaseFormValidator next) {
        this.nextValidator = next;
        return next;
    }

    public abstract ValidatorResult validate(HashMap<String, String> form);
    public ValidatorResult validateNext(HashMap<String, String> form) {
        if(nextValidator != null) {
            return nextValidator.validate(form);
        }
        LOGGER.info("========> NO VALIDATOR FOUND, END CHAIN");
        return ValidatorResult.success();
    }
}
