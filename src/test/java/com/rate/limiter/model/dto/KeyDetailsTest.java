package com.rate.limiter.model.dto;

import com.rate.limiter.utils.KeyDetailsConstants;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class KeyDetailsTest {

    @Test
    public void testKeyDetails() {
        HashMap<String, String> data = new HashMap<>();
        data.put(KeyDetailsConstants.KEY, "KEY");
        data.put(KeyDetailsConstants.WINDOW, "0");
        data.put(KeyDetailsConstants.MAX_REQUEST, "0");
        KeyDetails keyDetails = new KeyDetails(data);
        Assert.assertEquals(keyDetails.getKey(), "KEY");
        KeyDetails keyDetails1 = KeyDetails.builder()
                .key("key")
                .window(0l)
                .maxRequest(0l)
                .build();
        Assert.assertEquals("key", keyDetails1.getKey());
    }
}
