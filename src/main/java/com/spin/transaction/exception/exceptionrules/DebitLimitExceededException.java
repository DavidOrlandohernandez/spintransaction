package com.spin.transaction.exception.exceptionrules;

public class DebitLimitExceededException extends BusinessRuleException {

    public DebitLimitExceededException(String message) {
        super(message);
    }
}
