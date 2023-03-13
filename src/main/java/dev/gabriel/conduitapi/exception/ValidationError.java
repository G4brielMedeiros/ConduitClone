package dev.gabriel.conduitapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class ValidationError extends StandardError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Map<String, List<String>> errors = new HashMap<>();

    public ValidationError(String msg) {
        super(HttpStatus.UNPROCESSABLE_ENTITY.value(), msg);
    }

    public void addError(FieldError error) {
        errors.putIfAbsent(error.getField(), new ArrayList<>());
        errors.get(error.getField()).add(error.getDefaultMessage());
    }
}