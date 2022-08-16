package com.rate.limiter.core.errors;

import org.junit.Test;

public class WrongTypeExceptionTest {
    @Test(expected = RuntimeException.class)
    public void testError() {
        throw new WrongTypeException("class-name");
    }
}
