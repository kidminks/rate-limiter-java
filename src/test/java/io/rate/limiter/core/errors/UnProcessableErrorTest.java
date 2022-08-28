package io.rate.limiter.core.errors;

import io.github.kidminks.rate.limiter.core.errors.UnProcessableError;
import org.junit.Test;

public class UnProcessableErrorTest {
    @Test(expected = RuntimeException.class)
    public void testError() {
        throw new UnProcessableError("class-name");
    }
}
