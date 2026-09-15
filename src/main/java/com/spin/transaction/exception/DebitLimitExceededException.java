package com.spin.transaction.exception;

public class DebitLimitExceededException extends BusinessRuleException {

    public DebitLimitExceededException(String message) {
        super(message);
    }
}
