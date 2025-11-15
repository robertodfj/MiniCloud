package com.minicloud.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.minicloud.mapper.FileMapper;
import com.minicloud.model.FileMeta;
import com.minicloud.model.User;
import com.minicloud.repository.FileRepository;
import com.minicloud.repository.UserRepository;

@Service
public class FileService {
    
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private UserRepository userRepository;

    public FileMeta getFileMetaById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }

    public FileMeta getFileMetaByName(String fileName) {
        return fileRepository.findByFileName(fileName);
    }

    public FileMeta getFileMetaByNameAndUser(String fileName, String user) {
        User userEntity = userRepository.findByUserName(user);
        return fileRepository.findByFileNameAndUserCreator(fileName, userEntity);
    }

    public ResponseEntity<?> saveFileMeta(String fileName) {
         try {
            var fileOpt = getFileMetaByName(fileName);
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

    public ResponseEntity<?> uploadFile(MultipartFile file, Long userId) {
        try {
            User user = userRepository.getUserById(userId);
            FileMeta fileMeta = new FileMeta(file.getOriginalFilename(), user, file.getBytes());

            FileMeta savedFile = fileRepository.save(fileMeta);

            return ResponseEntity.ok(FileMapper.toDTO(savedFile)); 
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> getFilesByUser(String userName) {
        try {
            User user = userRepository.findByUserName(userName);
            return ResponseEntity.ok(fileRepository.findByUserCreator(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    public ResponseEntity<?> deleteFile(Long id) {
        try {
            fileRepository.deleteById(id);
            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }   
    }


}
