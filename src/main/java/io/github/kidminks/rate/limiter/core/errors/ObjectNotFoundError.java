package io.github.kidminks.rate.limiter.core.errors;

import io.github.kidminks.rate.limiter.utils.ErrorConstants;

public class ObjectNotFoundError extends RuntimeException {
    public ObjectNotFoundError(String object) {
        super(ErrorConstants.OBJECT_NOT_FOUND_ERROR + " " + object);
    }
}
