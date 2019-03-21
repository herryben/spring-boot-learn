package com.learn.controller;

import com.learn.bean.HttpResponse;
import com.learn.service.SpikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpikeController {

    @Autowired
    SpikeService spikeService;
    @RequestMapping("buy")
    public HttpResponse<String> buy(@RequestParam("sku") long sku, @RequestParam("user_id") long userId){
        if (spikeService.buy(sku, userId) > 0) {
            return new HttpResponse("抢购成功!");
        }
        return new HttpResponse("抢购失败!");
    }
}
