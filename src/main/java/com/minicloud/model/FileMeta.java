package com.minicloud.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="file_meta")
public class FileMeta {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;

    @Column(name="file_name")
    private String fileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User userCreator;

    @Lob  // Indica que es un campo grande (Large OBject)
    @Column(columnDefinition = "LONGBLOB")
    private byte[] data;

    public FileMeta() {
    }

    public FileMeta(String fileName, User userCreator, byte[] data) {
        this.fileName = fileName;
        this.userCreator = userCreator;
        this.data = data;
    }

    public FileMeta(String fileName, long id, User userCreator, byte[] data) {
        this.fileName = fileName;
        this.id = id;
        this.userCreator = userCreator;
        this.data = data;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

}