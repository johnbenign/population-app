package com.klasha.population.config;

import com.klasha.population.dto.response.GeneralResponse;
import com.klasha.population.exception.BadRequestException;
import com.klasha.population.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

	@ExceptionHandler(value = { NotFoundException.class })
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public GeneralResponse misMatchErrorHandler(NotFoundException ex) {
		log.info("throwing this::::::::::::: {}", ex.getMessage());
		return new GeneralResponse(HttpStatus.NOT_FOUND.value(), ex.getLocalizedMessage());
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public GeneralResponse handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex) {
		log.info("Method Argument not valid throwing....");

		List<String> errorList = ex.getBindingResult().getFieldErrors().stream()
				.map(fieldError -> fieldError.getDefaultMessage())
				.collect(Collectors.toList());

		return new GeneralResponse(HttpStatus.BAD_REQUEST.value(), errorList.get(0));

	}

	@ExceptionHandler(value = { IllegalArgumentException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public GeneralResponse illegalArgumentExceptionHandler(IllegalArgumentException ex) {
		return new GeneralResponse(HttpStatus.BAD_REQUEST.value(),
				ex.getLocalizedMessage());
	}

	@ExceptionHandler(value = { BadRequestException.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public GeneralResponse badRequestExceptionHandler(BadRequestException ex) {
		return new GeneralResponse(HttpStatus.BAD_REQUEST.value(),
				ex.getLocalizedMessage());
	}

	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public GeneralResponse exceptionHandler(Exception ex) {
		return new GeneralResponse(HttpStatus.BAD_REQUEST.value(),
				ex.getLocalizedMessage());
	}
}
