package io.github.kidminks.rate.limiter.model.dto;

import io.github.kidminks.rate.limiter.utils.LimitDetailsConstants;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.HashMap;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LimitDetails {
    @NotNull
    String key;

    @NotNull
    Long maxRequest;

    @NotNull
    Long window;

    public LimitDetails(HashMap<String, String> data) {
        this.key = data.getOrDefault(LimitDetailsConstants.KEY, "");
        this.window = Long.parseLong(data.getOrDefault(LimitDetailsConstants.WINDOW, "0"));
        this.maxRequest = Long.parseLong(data.getOrDefault(LimitDetailsConstants.MAX_REQUEST, "0"));
    }

    public String getLimitKey() {
        return this.key + "_limit";
    }
}
