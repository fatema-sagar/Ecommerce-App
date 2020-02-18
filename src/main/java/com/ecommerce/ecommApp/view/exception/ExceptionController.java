package com.ecommerce.ecommApp.view.exception;

import com.ecommerce.ecommApp.view.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestControllerAdvice
public class ExceptionController {

    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleException(HttpServletRequest req, Exception ex) {
        log.info("Catch Exceptions ----------\n");
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(HttpStatus.NOT_ACCEPTABLE,ex.getMessage(),null));
    }

}
