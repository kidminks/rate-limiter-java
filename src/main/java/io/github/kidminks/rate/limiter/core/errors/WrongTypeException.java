package io.github.kidminks.rate.limiter.core.errors;

import io.github.kidminks.rate.limiter.utils.ErrorConstants;

public class WrongTypeException extends RuntimeException {
    public WrongTypeException(String location) {
        super(ErrorConstants.WRONG_TYPE_ERROR + " " + location);
    }
}
