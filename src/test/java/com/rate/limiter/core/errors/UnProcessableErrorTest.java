package com.rate.limiter.core.errors;

import org.junit.Test;

public class UnProcessableErrorTest {
    @Test(expected = RuntimeException.class)
    public void testError() {
        throw new UnProcessableError("class-name");
    }
}
