package com.aims.son.validators;

import java.util.HashMap;

public interface IFormValidator {
    ValidatorResult validate(HashMap<String, String> form);
}
