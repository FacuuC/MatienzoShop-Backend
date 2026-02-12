package com.matienzoShop.celulares.events.dto;

import com.matienzoShop.celulares.events.model.EventType;
import jakarta.validation.constraints.NotNull;

public record EventRequestDTO (
        @NotNull
        EventType eventType,

        Long productId,
        String anonymousId,
        String metadata
) {}
