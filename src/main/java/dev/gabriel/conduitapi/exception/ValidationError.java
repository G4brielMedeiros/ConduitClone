package dev.gabriel.conduitapi.exception;

import dev.gabriel.conduitapi.validation.FieldErrorMessage;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ValidationError extends StandardError implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    public List<FieldErrorMessage> errors = new ArrayList<>();

    public ValidationError(Integer status, String msg, Long timeStamp) {
        super(status, msg, timeStamp);
    }

    public void addError(String name, String message) {
        errors.add(new FieldErrorMessage(name, message));
    }
}