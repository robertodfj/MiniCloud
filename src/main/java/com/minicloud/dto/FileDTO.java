package com.minicloud.dto;

import com.minicloud.model.User;

public class FileDTO {
    private Long id;
    private String fileName;
    private User userCreator;

    public FileDTO() {
    }

    public FileDTO(Long id, String fileName, User userCreator) {
        this.id = id;
        this.fileName = fileName;
        this.userCreator = userCreator;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public User getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(User userCreator) {
        this.userCreator = userCreator;
    }

    
}
