package com.spring.astrabill.backend.exceptions;

import com.spring.astrabill.backend.response.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request)
    {
        Map<String,String> validationErrors=new HashMap<>();
        List<ObjectError> validationErrorList=ex.getBindingResult().getAllErrors();

        validationErrorList.forEach((error)->{
            String fieldName=((FieldError) error).getField();
            String validationMessage=error.getDefaultMessage();
            validationErrors.put(fieldName,validationMessage);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGeneralException(Exception ex){
        ApiResponse apiResponse = new ApiResponse(
                ex.getMessage(),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse> handleBadCredentialsException(BadCredentialsException e){
        ApiResponse apiResponse=new ApiResponse(
                e.getMessage(),
                null,
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(apiResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse> handleUsernameNotFoundException(UsernameNotFoundException e){
        ApiResponse apiResponse=new ApiResponse(
                e.getMessage(),
                null,
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(apiResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictingResourcesException.class)
    public ResponseEntity<ApiResponse> handleConflictingResourcesException(ConflictingResourcesException e){
        ApiResponse apiResponse=new ApiResponse(
                e.getMessage(),
                null,
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT
        );
        return new ResponseEntity<>(apiResponse,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ForbiddenActivityException.class)
    public ResponseEntity<ApiResponse> handleForbiddenActivityException(ForbiddenActivityException e){
        ApiResponse apiResponse = new ApiResponse(
                e.getMessage(),
                null,
                HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN
        );
        return new ResponseEntity<>(apiResponse,HttpStatus.FORBIDDEN);
    }
}
