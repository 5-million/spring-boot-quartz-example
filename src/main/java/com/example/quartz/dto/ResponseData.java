package com.example.quartz.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseData <T> {
    private Date requestTime = new Date();
    private T data;

    public ResponseData(T data) {
        this.data = data;
    }
}
