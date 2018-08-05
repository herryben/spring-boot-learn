package com.learn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiService {
    private BaseService baseService;
    @Autowired
    public void setService(List<BaseService> baseServices) {
        System.out.println();
    }
}
