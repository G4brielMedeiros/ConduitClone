package dev.gabriel.conduitapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validation(MethodArgumentNotValidException exception) {

        ValidationError validationError = new ValidationError
                ("Validation Error", System.currentTimeMillis());

        exception.getBindingResult().getFieldErrors()
                .forEach(error -> validationError.addError(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.unprocessableEntity().body(validationError);
    }
}
