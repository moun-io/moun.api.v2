package io.moun.api.common;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

public class BaseEntityResponse {
    
    private LocalDateTime createdDate;
    
    private LocalDateTime lastModifiedDate;
}
