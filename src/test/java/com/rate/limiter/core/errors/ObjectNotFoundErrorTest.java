package com.rate.limiter.core.errors;

import org.junit.Test;

public class ObjectNotFoundErrorTest {

    @Test(expected = RuntimeException.class)
    public void testError() {
        throw new ObjectNotFoundError("class-name");
    }
}
