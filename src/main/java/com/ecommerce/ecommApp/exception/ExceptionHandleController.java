package com.ecommerce.ecommApp.exception;

import com.ecommerce.ecommApp.view.dto.response.ApiResponse;
import com.itextpdf.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;

@Slf4j
@RestControllerAdvice
public class ExceptionHandleController {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse> handleRunTimeException(HttpServletRequest req, Exception ex) {
        log.info("Catch Exceptions ----------\n");
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                .body(new ApiResponse(HttpStatus.NOT_ACCEPTABLE,ex.getMessage(),null));
    }

    @ExceptionHandler(DocumentException.class)
    public ResponseEntity<ApiResponse> handleDocumentException(HttpServletRequest request, Exception e) {
        log.info("Document Exception catch-----------\n");
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ApiResponse(HttpStatus.NOT_ACCEPTABLE, e.getMessage(),null));
    }

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<ApiResponse> handleFileNotFountException(HttpServletRequest request, Exception e) {
        log.info("File not found exception catch---------\n");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse(HttpStatus.NOT_FOUND, e.getMessage(),null));
    }

}
