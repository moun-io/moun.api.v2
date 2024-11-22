package io.moun.api.common.service;

import io.moun.api.common.domain.MounFile;
import io.moun.api.common.domain.MounFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MounFileService {

    @Value("${spring.servlet.multipart.location}")
    public String LOCAL_UPLOAD_DIR;
    private static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";

    private final MounFileRepository mounFileRepository;

    //upload to local file
    public MounFile uploadFileToLocalAndSave(MultipartFile file) {

        try {
            String fileName = generateUniqueFileName(file);
            
            String path = LOCAL_UPLOAD_DIR + "/" + fileName;
            file.transferTo(new File(path));
            
            MounFile uploadedFile = MounFile.builder()
                    .fileName(fileName)
                    .contentType(file.getContentType())
                    .filePath(path)
                    .build();
            return mounFileRepository.save(uploadedFile);
            
        } catch (Exception e) {
            throw new RuntimeException("No file uploaded");
        }
    }

    //download to web file
    public byte[] downloadFileFromLocal(String fileName) throws IOException {

        MounFile mounFileByFileName = getMounFileByFileName(fileName);

        String filePath = mounFileByFileName.getFilePath();
        log.info("file data: {}", mounFileByFileName);
        log.info("file path: {}", filePath);

        return Files.readAllBytes(new File(filePath).toPath());
    }

    //generate
    private String generateUniqueFileName(MultipartFile file) {

        if (file.isEmpty()) {
            return null;
        }
        
        String originName = file.getOriginalFilename();
        String uuid = UUID.randomUUID().toString();
        String fileExtension = originName.substring(originName.lastIndexOf("."));
        
        return "file_" + uuid + fileExtension;
    }

    public MounFile getMounFileById(Long id) {
        return mounFileRepository.findMounFileById(id)
                .orElseThrow(() -> new RuntimeException("Id not founded"));
    }

    public MounFile getMounFileByFileName(String fileName) {
        return mounFileRepository.findMounFileByFileName(fileName).orElseThrow(
                () -> new RuntimeException("Id not founded"));
    }
    
}
