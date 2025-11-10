package com.minicloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minicloud.dto.FileDTO;
import com.minicloud.dto.UserDTO;
import com.minicloud.mapper.FileMapper;
import com.minicloud.mapper.UserMapper;
import com.minicloud.model.User;
import com.minicloud.service.FileService;

@RestController
@RequestMapping("/api/files")
public class FileController {
    
    @Autowired
    private  FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(FileDTO fileDTO) {
        try {
            fileService.saveFileMeta(FileMapper.toModel(fileDTO));
            return ResponseEntity.ok("File uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/by-user")
    public ResponseEntity<?> getFilesByUser(UserDTO userDTO) {
        try {
            User user = UserMapper.toModel(userDTO);
            return ResponseEntity.ok(fileService.getFilesByUser(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFile(Long id) {
        try {
            fileService.deleteFile(id);
            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    

}
