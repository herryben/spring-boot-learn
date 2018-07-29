package com.learn.bean;


import java.io.Serializable;
public class User implements Serializable{
    private Long id;
    private Long userId;
    private String name;
    private String password;
    private String role;
    private Long addedTime;
    private Long modifiedTime;
    private static final long serialVersionUID = 1L;
    public User() {
    }

    public User(Long id, Long userId, String name, Long addedTime, Long modifiedTime) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.addedTime = addedTime;
        this.modifiedTime = modifiedTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(Long addedTime) {
        this.addedTime = addedTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", addedTime=" + addedTime +
                ", modifiedTime=" + modifiedTime +
                '}';
    }
}
