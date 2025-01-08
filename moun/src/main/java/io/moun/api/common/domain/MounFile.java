package io.moun.api.common.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MounFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    private String fileName;

    private String generatedFileName;
    private String contentType;
    private String filePath;

    @Builder
    public MounFile(String fileName, String generatedFileName, String contentType, String filePath) {
        this.fileName = fileName;
        this.generatedFileName = generatedFileName;
        this.contentType = contentType;
        this.filePath = filePath;
    }
}
