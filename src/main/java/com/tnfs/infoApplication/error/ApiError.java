package com.tnfs.infoApplication.error;

import lombok.Data;
import org.apache.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public
class ApiError {

    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private String path;
    private String method;
    private List<ApiSubError> subErrors;

}
