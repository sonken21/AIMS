package com.aims.group19.validators;

import com.aims.group19.validators.handlers.*;

import java.util.HashMap;

public class DeliveryFormValidatorContext implements IFormValidator{
    private final BaseFormValidator validator;

    public DeliveryFormValidatorContext() {
        validator = ValidatorHelpers.chain(
            new RequiredFieldValidatorHandler(new String[] {"name", "email", "phone", "province", "address", "rushDelivery"}),
            new NameFieldValidatorHandler(),
            new EmailFieldValidatorHandler(),
            new PhoneFieldValidatorHandler(),
            new ProvinceFieldValidatorHandler(),
            new AddressFieldValidatorHandler(),
            new RushDeliveryValidatorHandler()
        );
    }
    @Override
    public ValidatorResult validate(HashMap<String, String> form) {
        return validator.validate(form);
    }
}
