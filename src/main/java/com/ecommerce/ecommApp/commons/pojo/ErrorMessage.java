package com.ecommerce.ecommApp.commons.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErrorMessage {
    @JsonProperty(value="message")
    private String message;
}
