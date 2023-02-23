package dev.gabriel.conduitapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

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

    public ValidationError(String msg, Long timeStamp) {
        super(HttpStatus.UNPROCESSABLE_ENTITY.value(), msg, timeStamp);
    }

    public void addError(String name, String message) {
        errors.putIfAbsent(name, new ArrayList<>());
        errors.get(name).add(message);
    }
}