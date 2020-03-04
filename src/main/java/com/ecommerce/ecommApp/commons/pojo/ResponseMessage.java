package com.ecommerce.ecommApp.commons.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {

    private HttpStatus httpStatus;
    private String message;
    private String type;
    private Object data;

    public ResponseMessage(String message, String type) {
        this.message = message;
        this.type = type;
    }

    public ResponseMessage(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ResponseMessage(HttpStatus httpStatus, String message) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public ResponseMessage(HttpStatus httpStatus,String message, Object data) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.data = data;
    }
}
