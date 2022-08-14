package com.rate.limiter.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LimitDetails {
    @NotNull
    String id;

    @NotNull
    Long maxRequest;

    @NotNull
    Long window;

}
