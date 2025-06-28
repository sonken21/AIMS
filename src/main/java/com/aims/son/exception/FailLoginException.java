package com.aims.son.exception;

public class FailLoginException extends AimsException {

    public FailLoginException() {
        super("ERROR: Fail to Login. Please try again!");
    }
}