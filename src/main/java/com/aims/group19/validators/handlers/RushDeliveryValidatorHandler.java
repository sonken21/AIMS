package com.aims.group19.validators.handlers;

import com.aims.group19.utils.Utils;
import com.aims.group19.validators.BaseFormValidator;
import com.aims.group19.validators.ValidatorHelpers;
import com.aims.group19.validators.ValidatorResult;

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
