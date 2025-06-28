package com.aims.son.validators.handlers;

import com.aims.son.utils.Utils;
import com.aims.son.validators.BaseFormValidator;
import com.aims.son.validators.ValidatorHelpers;
import com.aims.son.validators.ValidatorResult;

import java.util.HashMap;
import java.util.logging.Logger;

public class RushDeliveryValidatorHandler extends BaseFormValidator {
    private final BaseFormValidator rushDeliveryChainValidator;
    private static Logger LOGGER = Utils.getLogger(RushDeliveryValidatorHandler.class.getName());

    public RushDeliveryValidatorHandler() {
        rushDeliveryChainValidator = ValidatorHelpers.chain(
            new RequiredFieldValidatorHandler(new String[]{"deliveryTime", "deliveryInstruction"}),
            new DeliveryTimeValidatorHandler(),
            new DeliveryInstructionFieldValidatorHandler()
        );
    }
    @Override
    public ValidatorResult validate(HashMap<String, String> form) {
        String rushDelivery = form.get("rushDelivery");
        if("true".equalsIgnoreCase(rushDelivery)){
            LOGGER.info("========> Starting Rush Delivery Validator Chain");
            return rushDeliveryChainValidator.validate(form);
        }
        return ValidatorResult.success();
    }
}
