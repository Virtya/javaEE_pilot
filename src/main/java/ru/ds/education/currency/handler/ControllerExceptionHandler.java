package ru.ds.education.currency.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.ds.education.currency.dto.error.ErrorDto;
import ru.ds.education.currency.exception.ResourceAlreadyExistException;
import ru.ds.education.currency.exception.ResourceNotFoundException;

import java.time.LocalDate;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public @ResponseBody ResponseEntity<ErrorDto> handleResourceNotFoundException(Exception e){

        LocalDate timestamp = LocalDate.now();

        return new ResponseEntity<>(
                    new ErrorDto(e.getMessage(),timestamp),
                    HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ResourceAlreadyExistException.class)
    public @ResponseBody ResponseEntity<ErrorDto> handleResourceAlreadyExistsException(Exception e){

        LocalDate timestamp = LocalDate.now();

        return new ResponseEntity<>(
                    new ErrorDto(e.getMessage(),timestamp),
                    HttpStatus.CONFLICT
        );
    }
}
