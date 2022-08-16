package com.rate.limiter.model.dto;

import com.rate.limiter.utils.KeyDetailsConstants;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KeyDetails {
    @NotNull
    private String key;

    @NotNull
    private Long window;

    @NotNull
    private Long maxRequest;

    public KeyDetails(HashMap<String, String> data) {
        this.key = data.getOrDefault(KeyDetailsConstants.KEY, "");
        this.window = Long.parseLong(data.getOrDefault(KeyDetailsConstants.WINDOW, "0"));
        this.maxRequest = Long.parseLong(data.getOrDefault(KeyDetailsConstants.MAX_REQUEST, "0"));
    }
}
