package com.example.springpostgres.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.springpostgres.model.WebResponse;
import com.example.springpostgres.service.FileUploadService;

@RestController
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping(
        path = "/upload",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> upload(@RequestPart(name = "file") MultipartFile file){
        
        String fileUpload = fileUploadService.uploadFile(file);

        return WebResponse.<String>builder().code(HttpStatus.OK.value()).message("OK").data(fileUpload).build();

    }

    @GetMapping(
        path = "/image/{fileName}",
        produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<Resource> image(@PathVariable("fileName") String filename){
        
        Resource resource = fileUploadService.getFile(filename);

        return ResponseEntity.ok().body(resource);

    }

}
