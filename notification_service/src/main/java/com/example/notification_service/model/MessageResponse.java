package com.example.notification_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse<T> {
    private Long client;
    private Integer status;
    private String message;
    private T data;

    public void success(T data){
        this.data = data;
        status = HttpStatus.OK.value();
        message = "OK";
    }

    public void error(String message){
        status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.message = message;
    }
}
