package com.learn.bean;

import java.io.Serializable;

public class User implements Serializable{
    private Long id;
    private Long userId;
    private String userName;
    private Long addedTime;
    private Long modefiedTime;
    private static final long serialVersionUID = 1L;
    public User() {
    }

    public User(Long userId, String userName, Long addedTime, Long modefiedTime) {
        this.userId = userId;
        this.userName = userName;
        this.addedTime = addedTime;
        this.modefiedTime = modefiedTime;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Long getAddedTime() {
        return addedTime;
    }

    public Long getModefiedTime() {
        return modefiedTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", addedTime=" + addedTime +
                ", modefiedTime=" + modefiedTime +
                '}';
    }
}
