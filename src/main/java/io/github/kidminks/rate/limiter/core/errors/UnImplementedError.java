package io.github.kidminks.rate.limiter.core.errors;

import io.github.kidminks.rate.limiter.utils.ErrorConstants;

public class UnImplementedError extends RuntimeException {
    public UnImplementedError(String location) {
        super(ErrorConstants.UN_IMPLEMENTED_ERROR + " " + location);
    }
}
