package com.minicloud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minicloud.model.FileMeta;
import com.minicloud.model.User;
import com.minicloud.repository.FileRepository;

@Service
public class FileService {
    
    @Autowired
    private FileRepository fileRepository;

    public FileMeta saveFileMeta(FileMeta fileMeta) {
        return fileRepository.save(fileMeta);
    }

    public FileMeta getFileMetaById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }

    public FileMeta getFileMetaByNameAndUser(String fileName, String user) {
        return fileRepository.findByNameAndUserCreator(fileName, user);
    }

    public  List<FileMeta> getFilesByUser(User user) {
        return fileRepository.findByUserCreator(user);
    }

    public void deleteFile(Long id) {
        fileRepository.deleteById(id);
    }


}
