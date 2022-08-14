package com.rate.limiter.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
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
