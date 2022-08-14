package com.rate.limiter.model.dto;

import org.junit.Assert;
import org.junit.Test;

public class LimitDetailsTest {

    @Test
    public void testLimitDetails() {
        LimitDetails limitDetails = LimitDetails.builder().id("1").maxRequest(100L).window(100000L).build();
        Assert.assertEquals(limitDetails.getId(),"1");
        Assert.assertEquals(limitDetails.getMaxRequest().longValue(), 100l);
        Assert.assertEquals(limitDetails.getWindow().longValue(), 100000l);
    }
}
