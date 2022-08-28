package io.rate.limiter.core.errors;

import io.github.kidminks.rate.limiter.core.errors.WrongTypeException;
import org.junit.Test;

public class WrongTypeExceptionTest {
    @Test(expected = RuntimeException.class)
    public void testError() {
        throw new WrongTypeException("class-name");
    }
}
