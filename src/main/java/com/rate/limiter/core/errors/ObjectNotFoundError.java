package com.rate.limiter.core.errors;

import com.rate.limiter.utils.ErrorConstants;

public class ObjectNotFoundError extends RuntimeException {
    public ObjectNotFoundError(String object) {
        super(ErrorConstants.OBJECT_NOT_FOUND_ERROR + " " + object);
    }
}
