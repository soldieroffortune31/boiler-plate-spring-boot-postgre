package com.example.springpostgres.util;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import com.example.springpostgres.exception.FileStorageException;

public class FileStorageUtil {

    private static final String UPLOAD_DIR = "upload/";

    public static String saveFile(MultipartFile file){
        try {
            
            Files.createDirectories(Paths.get(UPLOAD_DIR));
    
            String originalFileName = file.getOriginalFilename();
            String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
    
            String modifiedFileName = UUID.randomUUID().toString() + "-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + fileExtension;
    
            Path filePath = Path.of(UPLOAD_DIR + modifiedFileName);
    
            file.transferTo(filePath);
    
            return modifiedFileName;
        } catch (Exception e) {
            throw new FileStorageException("File upload failed: " + e.getMessage());
        }

        
    }

    public static Resource getFile(String filename){

        Path filePath = Paths.get(UPLOAD_DIR).resolve(filename);
        try {
            Resource resource = new UrlResource(filePath.toUri());

            return resource;
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found: " + filename, e);
        }

    }


}
