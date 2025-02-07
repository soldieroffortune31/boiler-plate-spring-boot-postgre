package com.example.springpostgres.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.springpostgres.util.FileStorageUtil;

@Service
public class FileUploadService {

    public String uploadFile(MultipartFile file){
        return FileStorageUtil.saveFile(file);
    }

    public Resource getFile(String filename){
        return FileStorageUtil.getFile(filename);
    }

}
