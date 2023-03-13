package dev.gabriel.conduitapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;


@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> globalException() {

        StandardError error = new StandardError(HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationError(MethodArgumentNotValidException exception) {

        ValidationError validationError = new ValidationError("Validation Error");

        exception.getBindingResult().getFieldErrors()
                .forEach(validationError::addError);

        return ResponseEntity.unprocessableEntity().body(validationError);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> constraintError() {

        StandardError error = new StandardError(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An error occured attempting to persist the given entity."
        );

        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> badHttp() {

        StandardError error = new StandardError(HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(error);
    }
}
