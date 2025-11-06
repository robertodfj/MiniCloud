package com.minicloud.dto;

public class FileDTO {
    private Long id;
    private String fileName;
    private UserDTO userCreator;

    public FileDTO() {
    }

    public FileDTO(Long id, String fileName, UserDTO userCreator) {
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

    public UserDTO getUserCreator() {
        return userCreator;
    }

    public void setUserCreator(UserDTO userCreator) {
        this.userCreator = userCreator;
    }

    
}
