package com.learn.bean;


import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
@TableName("user")
public class User implements Serializable{
    private Long id;
    private Long userId;
    private String name;
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

    public Long getmodifiedTime() {
        return modifiedTime;
    }

    public void setmodifiedTime(Long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", addedTime=" + addedTime +
                ", modifiedTime=" + modifiedTime +
                '}';
    }
}
