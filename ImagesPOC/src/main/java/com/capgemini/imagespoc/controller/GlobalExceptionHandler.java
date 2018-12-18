package com.capgemini.imagespoc.controller;
import com.capgemini.imagespoc.exception.*;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="IOException occured")
	@ExceptionHandler(IOException.class)
	public String  handleIOException() {
		logger.error("IOException handler executed");
		return "IO exception occured...Check the input file";
	}

	@ExceptionHandler(ImageNotFoundException.class)
	public String handleImageNotFoundException(String message, Exception ex){
		logger.error(message);
		ex.printStackTrace();
		return message ;
	}
	
	
}






