package io.rate.limiter.core.errors;

import io.github.kidminks.rate.limiter.core.errors.ObjectNotFoundError;
import org.junit.Test;

public class ObjectNotFoundErrorTest {

    @Test(expected = RuntimeException.class)
    public void testError() {
        throw new ObjectNotFoundError("class-name");
    }
}
