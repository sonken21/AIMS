package com.aims.group19.validators;

import java.util.HashMap;

public interface IFormValidator {
    ValidatorResult validate(HashMap<String, String> form);
}
