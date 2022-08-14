package com.rate.limiter.core.errors;

import com.rate.limiter.utils.ErrorConstants;

public class UnProcessableError extends RuntimeException {
    public UnProcessableError(String object) {
        super(ErrorConstants.UN_PROCESSABLE_ENTITY + object);
    }
}
