package com.rate.limiter.core.errors;

import com.rate.limiter.utils.ErrorConstants;

public class WrongTypeException extends RuntimeException {
    public WrongTypeException(String location) {
        super(ErrorConstants.WRONG_TYPE_ERROR + " " + location);
    }
}
