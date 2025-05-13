package com.epam.pwt.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomeExceptionHandler extends ResponseEntityExceptionHandler{
	
	
	 @Override
	   protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
	       String error = "Malformed JSON request";
	       return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	   }

	   private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		   apiError.setTimestamp(LocalDateTime.now());
	       return new ResponseEntity<>(apiError, apiError.getStatus());
	   }
	   
	   
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest req){
		
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
	    apiError.setMessage(ex.getMessage());
		
		return buildResponseEntity(apiError);
		
	}
	
	@ExceptionHandler(GroupNotFoundException.class)
	public final ResponseEntity<Object> handleGroupNotFoundException(GroupNotFoundException ex, WebRequest req){
		
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
	    apiError.setMessage(ex.getMessage());
		
		return buildResponseEntity(apiError);
		
		
	}
	
	@ExceptionHandler(AccountNotFoundException.class)
	public final ResponseEntity<Object> handleAccoutNotFoundException(AccountNotFoundException ex, WebRequest req){
		
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
	    apiError.setMessage(ex.getMessage());
		
		return buildResponseEntity(apiError);
		
	}
    
	@ExceptionHandler(DuplicateAccountException.class)
	public final ResponseEntity<Object> handleDuplicateAccountException(DuplicateAccountException ex, WebRequest req){
		
		ApiError apiError = new ApiError(HttpStatus.CONFLICT);
	    apiError.setMessage(ex.getMessage());
		
		return buildResponseEntity(apiError);
		
	}
	
	@ExceptionHandler(DuplicateGroupException.class)
	public final ResponseEntity<Object> handleDuplicateGroupException(DuplicateGroupException ex, WebRequest req){
		
		ApiError apiError = new ApiError(HttpStatus.CONFLICT);
	    apiError.setMessage(ex.getMessage());
		
		return buildResponseEntity(apiError);
		
	}
	
	@ExceptionHandler(DuplicateUserAccountException.class)
	public final ResponseEntity<Object> handleDuplicateUserAccountException(DuplicateUserAccountException ex, WebRequest req){
		
		String message="User with Username or Email already exist"; 
		ApiError apiError = new ApiError(HttpStatus.CONFLICT);
	    apiError.setMessage(message);
		return buildResponseEntity(apiError);
		
	}
	
	@ExceptionHandler(NonEmptyGroupExceptions.class)
	public final ResponseEntity<Object> handleNonEmptyGroupException(NonEmptyGroupExceptions ex, WebRequest req){
		
		ApiError apiError = new ApiError(HttpStatus.NOT_ACCEPTABLE);
	    apiError.setMessage(ex.getMessage());
		
		return buildResponseEntity(apiError);
		
	}
	
//	@ExceptionHandler(ServletRe)
	
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
     
            List<String> details = new ArrayList<>();
            for(ObjectError error : ex.getBindingResult().getAllErrors()) {
                details.add(error.getDefaultMessage());
            }
            ApiError apiError = new ApiError(HttpStatus.CONFLICT);
    	    apiError.setMessage("Validation Constraint Failed");
    		apiError.setSubErrors(details);
    		return buildResponseEntity(apiError);
    		

    }
}
