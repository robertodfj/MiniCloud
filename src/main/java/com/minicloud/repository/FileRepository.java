package com.minicloud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.minicloud.model.FileMeta;
import com.minicloud.model.User;

public interface FileRepository extends JpaRepository<FileMeta, Long> {
    
    List<FileMeta> findByUserCreator(User userCreator);
    FileMeta findByNameAndUserCreator(String fileName, User userCreator);
}
