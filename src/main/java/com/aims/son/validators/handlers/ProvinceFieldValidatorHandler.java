package com.aims.son.validators.handlers;

import com.aims.son.utils.Utils;
import com.aims.son.validators.BaseFormValidator;
import com.aims.son.validators.ValidatorResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

import static com.aims.son.utils.Configs.PROVINCES;

public class ProvinceFieldValidatorHandler extends BaseFormValidator {
    private static Logger LOGGER = Utils.getLogger(PhoneFieldValidatorHandler.class.getName());

    @Override
    public ValidatorResult validate(HashMap<String, String> form) {
        LOGGER.info("========> Validating Province Field ...");
        String province = form.get("province");
        if(!Arrays.asList(PROVINCES).contains(province)){
            LOGGER.info("========> Validate Failed");
            return ValidatorResult.error("Invalid Province");
        }
        LOGGER.info("========> Validate Successful");
        return validateNext(form);
    }
}
