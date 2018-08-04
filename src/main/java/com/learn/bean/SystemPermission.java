package com.learn.bean;

public class SystemPermission {
    private Long id;
    private String url;
    private String role;
    private int sort;
    private long modifiedTime;

    public SystemPermission() {
    }

    public SystemPermission(String url, String role, int sort, long modifiedTime) {
        this.url = url;
        this.role = role;
        this.sort = sort;
        this.modifiedTime = modifiedTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    @Override
    public String toString() {
        return "SystemPermission{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", role='" + role + '\'' +
                ", sort=" + sort +
                ", modifiedTime=" + modifiedTime +
                '}';
    }
}
