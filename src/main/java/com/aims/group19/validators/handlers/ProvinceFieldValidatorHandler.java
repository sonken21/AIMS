package com.aims.group19.validators.handlers;

import com.aims.group19.utils.Utils;
import com.aims.group19.validators.BaseFormValidator;
import com.aims.group19.validators.ValidatorResult;

import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Logger;

import static com.aims.group19.utils.Configs.PROVINCES;

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
