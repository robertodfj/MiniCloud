package com.minicloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.minicloud.service.FileService;


@RestController
@RequestMapping("/api/files")
public class FileController {
    
    @Autowired
    private  FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, 
                                        @RequestParam("userId") Long userId) {
        return fileService.uploadFile(file, userId);
       
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable("filename") String fileName) {
        return fileService.saveFileMeta(fileName);
    }

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<?> getFilesByUser(@PathVariable("userName") String userName) {
        return fileService.getFilesByUser(userName);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable("id") Long id) {
        return fileService.deleteFile(id);
    }
    

}
