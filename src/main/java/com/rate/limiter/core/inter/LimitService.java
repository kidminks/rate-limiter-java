package com.rate.limiter.core.inter;

import com.rate.limiter.model.dto.LimitDetails;

import javax.validation.Valid;

public interface LimitService {
    void setOrUpdateLimit(@Valid LimitDetails limitDetails);
    void deleteLimit(String id);
    LimitDetails getLimit(String limitId);
}
