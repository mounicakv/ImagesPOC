package com.capgemini.imagespoc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Image Not Found") // 404
public class ImageNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -3332292346834265371L;
	
	public ImageNotFoundException(String message, Throwable e){
		super(message, e);
	}

}
