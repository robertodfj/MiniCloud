package com.minicloud.controller;

import java.io.IOException;

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

import com.minicloud.mapper.FileMapper;
import com.minicloud.model.FileMeta;
import com.minicloud.model.User;
import com.minicloud.service.FileService;
import com.minicloud.service.UserService;

@RestController
@RequestMapping("/api/files")
public class FileController {
    
    @Autowired
    private  FileService fileService;

    @Autowired
    private UserService userService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file, 
                                        @RequestParam("userId") Long userId) {
        try {
            User user = userService.getUserById(userId);
            FileMeta fileMeta = new FileMeta(file.getOriginalFilename(), user, file.getBytes());

            FileMeta savedFile = fileService.saveFileMeta(fileMeta);

            return ResponseEntity.ok(FileMapper.toDTO(savedFile)); 
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
       
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<?> downloadFile(@PathVariable("filename") String fileName) {
        try {
            var fileOpt = fileService.getFileMetaByName(fileName);
            if (fileOpt == null) {
                return ResponseEntity.notFound().build();
            }
            FileMeta fileMeta = fileOpt;
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + fileMeta.getFileName() + "\"")
                    .body(fileMeta.getData());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<?> getFilesByUser(@PathVariable("userName") String userName) {
        try {
            User user = userService.getUserByUsername(userName);
            return ResponseEntity.ok(fileService.getFilesByUser(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable("id") Long id) {
        try {
            fileService.deleteFile(id);
            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    

}
