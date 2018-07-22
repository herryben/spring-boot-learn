package com.learn.bean;

import java.io.Serializable;

public class HttpResponse<T> implements Serializable{
    private HeaderStatus header = HeaderStatus.SUCCESS;
    private T body;

    public HttpResponse() {
    }

    public HttpResponse(T body) {
        this.body = body;
    }

    public HttpResponse(HeaderStatus header) {
        this.header = header;
    }

    public HttpResponse(HeaderStatus header, T body) {
        this.header = header;
        this.body = body;
    }

    public HeaderStatus getHeader() {
        return header;
    }

    public void setHeader(HeaderStatus header) {
        this.header = header;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "header=" + header +
                ", body=" + body +
                '}';
    }
}
