package com.jphilips.twittercopy.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jphilips.twittercopy.dto.ExceptionResponseDTO;
import com.jphilips.twittercopy.dto.FieldErrorResponseDTO;
import com.jphilips.twittercopy.exception.custom.CustomValidationException;

import io.jsonwebtoken.JwtException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    // to catch exceptions from filter
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ExceptionResponseDTO> handleJwtException(JwtException ex) {
    	return formatExceptionResponse(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }
    
    // using custom exception to avoid method parameter argument of MethodArgumentNotValidException
    @ExceptionHandler(value = CustomValidationException.class)
    public ResponseEntity<FieldErrorResponseDTO> handleFieldErrors(CustomValidationException ex){
    	
    	FieldErrorResponseDTO fieldErrorResponseDTO = new FieldErrorResponseDTO();
    	
    	if (ex.getBindingResult().hasErrors()) {
    		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
    			fieldErrorResponseDTO.addError(error.getField(), error.getDefaultMessage());
        	}
		}
    	
    	return new ResponseEntity<>(fieldErrorResponseDTO, HttpStatus.BAD_REQUEST);
    	
    }

    // General Exception handler
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionResponseDTO> handleException(Exception ex) {
		
		logger.error("Unhandled exception caught: ", ex);
		
		return formatExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "");
		
	}
	
	// helper methods
	
	public static ResponseEntity<ExceptionResponseDTO> formatExceptionResponse(HttpStatus status,  String message) {
		
		if (message.isBlank()) {
			message = "Please contact your Administrator";
		}
		
		ExceptionResponseDTO responseDTO = new ExceptionResponseDTO(
										LocalDateTime.now(), 
										status.value(),
										status.getReasonPhrase(),
										message);
		
		return new ResponseEntity<>(responseDTO,status);
	}
	
}
