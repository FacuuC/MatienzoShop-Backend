package com.matienzoShop.celulares.events.dto;

import com.matienzoShop.celulares.events.model.EventType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

public record EventRequestDTO (
        @NotNull
        EventType eventType,
        Long productId,
        String anonymousId,
        Map<String, Object> metadata
) {

        public static Builder builder(){
                return new Builder();
        }

        public static class Builder {
                private EventType eventType;
                private Long productId;
                private String anonymousId;
                private Map<String, Object> metadata;

                public Builder eventType (EventType eventType){
                        this.eventType = eventType;
                        return this;
                }

                public Builder productId(Long productId) {
                        this.productId = productId;
                        return this;
                }

                public Builder anonymousId(String anonymousId) {
                        this.anonymousId = anonymousId;
                        return this;
                }

                public Builder metadata(Map<String, Object> metadata) {
                        this.metadata = metadata;
                        return this;
                }

                public EventRequestDTO build() {
                        return new EventRequestDTO(
                                eventType,
                                productId,
                                anonymousId,
                                metadata
                        );
                }
        }
}
