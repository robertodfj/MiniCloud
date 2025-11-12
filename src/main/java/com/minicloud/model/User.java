package com.minicloud.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="user_id")
    private long id;

    @NotBlank(message = "El nombre de usuario no puede estar en blanco")
    @Column(name= "user_name", unique = true) 
    private String userName;

    @NotBlank(message = "El email de usuario no puede estar en blanco")
    @Column(name= "user_email", unique = true)  
    private String email;

    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Column(name="user_password")
    private String password;

    @OneToOne(mappedBy = "roleUser")
    private ROLE role;

    private boolean authenticated = false;

    private int authenticationToken;

    public User() {
    }

    public User(String email, long id, String password, String userName, ROLE role, boolean authenticated, int authenticationToken) {
        this.email = email;
        this.id = id;
        this.password = password;
        this.userName = userName;
        this.role = role;
        this.authenticated = authenticated;
        this.authenticationToken = authenticationToken;
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

    public ROLE getRole() {
        return role;
    }

    public void setRole(ROLE role) {
        this.role = role;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public int getAuthenticationToken() {
        return authenticationToken;
    }

    public void setAuthenticationToken(int authenticationToken) {
        this.authenticationToken = authenticationToken;
    }

}
