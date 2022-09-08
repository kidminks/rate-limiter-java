package io.github.kidminks.rate.limiter.core.errors;

import io.github.kidminks.rate.limiter.utils.ErrorConstants;

public class UnProcessableError extends RuntimeException {
    public UnProcessableError(String object) {
        super(ErrorConstants.UN_PROCESSABLE_ENTITY + object);
    }
}
