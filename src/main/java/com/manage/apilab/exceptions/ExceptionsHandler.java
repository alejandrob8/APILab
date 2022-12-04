package com.manage.apilab.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

	// STATUS CODE 200
	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(OKException.class)
	public ResponseEntity<ErrorFormat> OKHandler(Exception e) {

		ErrorFormat error = new ErrorFormat();
		error.setMessage(e.getMessage());
		error.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<>(error, HttpStatus.OK);

	}

	// STATUS CODE 201
	@ResponseStatus(HttpStatus.CREATED)
	@ExceptionHandler(CreatedException.class)
	public ResponseEntity<ErrorFormat> CreatedHandler(Exception e) {

		ErrorFormat error = new ErrorFormat();
		error.setMessage(e.getMessage());
		error.setStatusCode(HttpStatus.CREATED.value());
		return new ResponseEntity<>(error, HttpStatus.CREATED);

	}

	// STATUS CODE 204
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ExceptionHandler(NoContentException.class)
	public ResponseEntity<Object> NoContentHandler() {
		// Body empty
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// STATUS CODE 404
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({ NotFoundException.class, NullPointerException.class, RuntimeException.class })
	public ResponseEntity<ErrorFormat> NotFoundHandler(Exception e) {

		ErrorFormat error = new ErrorFormat();
		error.setMessage(e.getMessage());
		error.setStatusCode(HttpStatus.NOT_FOUND.value());
		if (e instanceof NullPointerException) {
			error.setMessage("Null pointer");
		}
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

	}

	// STATUS CODE 500
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorFormat> InternalServerHandler(Exception e) {

		ErrorFormat error = new ErrorFormat();
		error.setMessage(e.getMessage());
		error.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
