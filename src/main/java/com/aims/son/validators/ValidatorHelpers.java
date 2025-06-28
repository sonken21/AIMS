package com.aims.son.validators;

public class ValidatorHelpers {
    public static BaseFormValidator chain(BaseFormValidator first, BaseFormValidator... chain){
        BaseFormValidator head = first;
        for (BaseFormValidator validator : chain){
            head.setNext(validator);
            head = validator;
        }
        return first;
    }
}
