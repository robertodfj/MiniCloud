package com.minicloud.model;

import jakarta.persistence.*;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
@Entity
@Table(name="Users")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "El nombre de usuario no puede estar en blanco")
    @Column (unique = true)
    private String userName;

    @NotBlank(message = "El email de usuario no puede estar en blanco")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "La contraseña no puede estar en blanco")
    private String password;

    public User() {
    }

    public User(String email, long id, String password, String userName) {
        this.email = email;
        this.id = id;
        this.password = password;
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
