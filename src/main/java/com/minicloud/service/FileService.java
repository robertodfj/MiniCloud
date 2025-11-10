package com.minicloud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.minicloud.model.FileMeta;
import com.minicloud.repository.FileRepository;

@Service
public class FileService {
    
    @Autowired
    private static FileRepository fileRepository;

    public static FileMeta saveFileMeta(FileMeta fileMeta) {
        return fileRepository.save(fileMeta);
    }

    public static FileMeta getFileMetaByNameAndUser(String fileName, com.minicloud.model.User user) {
        return fileRepository.findByNameAndUserCreator(fileName, user);
    }

    public static java.util.List<FileMeta> getFilesByUser(com.minicloud.model.User user) {
        return fileRepository.findByUserCreator(user);
    }

    
}
