package io.moun.api.common;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntityResponse {
    
    private LocalDateTime createdDate;
    
    private LocalDateTime lastModifiedDate;

    public BaseEntityResponse(LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
}
