package com.learn.controller;

import com.learn.bean.User;
import lombok.val;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class HttpCacheController {
    private Map<String, Long> timeCache = new HashMap<>();

    @RequestMapping("cache")
    public ResponseEntity<String> cahce(@RequestHeader(value = "If-Modified-Since", required = false) Date ifModifiedSince) {
        DateFormat gmtDateFormat = new SimpleDateFormat("EEE, d MMM yyy HH:mm:ss 'GMT'", Locale.US);
        long now = System.currentTimeMillis() / 1000 * 1000;
        long lastModifiedMills = timeCache.get("cache") != null ? timeCache.get("cache") : now;
        long maxAge = 10;
        if (ifModifiedSince != null && now - lastModifiedMills > 0 && now - lastModifiedMills <= maxAge * 1000) {
            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.add("Date", gmtDateFormat.format(now));
            headers.add("Expire", gmtDateFormat.format(new Date(now + maxAge * 1000)));
            headers.add("Cache-Control", "max-age=" + maxAge);
            return new ResponseEntity<String>(headers, HttpStatus.NOT_MODIFIED);
        }
        String body = "This is html body";
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Date", gmtDateFormat.format(new Date(now)));
        headers.add("Last-Modified", gmtDateFormat.format(new Date(lastModifiedMills)));
        headers.add("Expire", gmtDateFormat.format(new Date(now + maxAge * 1000)));
        headers.add("Cache-Control", "max-age=" + maxAge);
        timeCache.put("cache", now);
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    public static void main(String[] args) {
        User user = new User();
        Long id = user.getId();
    }
}
