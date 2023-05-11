package dev.gabriel.conduitapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.unprocessableEntity;


@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> globalException(Exception e) {
        return internalServerError("An unknown error occurred during this operation: " + e.getClass().getSimpleName());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<StandardError> badCredentials(BadCredentialsException e) {
        return badRequest(e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<StandardError> constraintError() {
        return internalServerError("An error occurred attempting to persist the given entity");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<StandardError> badHttp() {
        return badRequest("Invalid JSON");
    }

    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Void> entityNotFound() {
        return notFound().build();
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<StandardError> unsupportedMethod(HttpRequestMethodNotSupportedException exception) {
        return methodNotAllowed(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationError(MethodArgumentNotValidException exception) {
        return unprocessableEntity()
                .body(new ValidationError("Validation Error", exception.getBindingResult().getFieldErrors()));
    }

    private static ResponseEntity<StandardError> badRequest(String msg) {
        return ResponseEntity.badRequest()
                .body(new StandardError(HttpStatus.BAD_REQUEST, msg));
    }

    private static ResponseEntity<StandardError> internalServerError(String msg) {
        return ResponseEntity.internalServerError()
                .body(new StandardError(HttpStatus.INTERNAL_SERVER_ERROR, msg));
    }

    private static ResponseEntity<StandardError> methodNotAllowed(String msg) {
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new StandardError(HttpStatus.METHOD_NOT_ALLOWED, msg));
    }
}
