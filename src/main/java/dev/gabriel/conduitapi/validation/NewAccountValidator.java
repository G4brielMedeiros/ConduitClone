package dev.gabriel.conduitapi.validation;

import dev.gabriel.conduitapi.dto.NewAccountDTO;
import dev.gabriel.conduitapi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class NewAccountValidator implements ConstraintValidator<ValidNewAccount, NewAccountDTO> {

    public static final String PASSWORD_POLICY = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
    public static final String USERNAME_POLICY = "^[a-zA-Z0-9_-]{8,}$";
    final AccountRepository repository;

    @Override
    public void initialize(ValidNewAccount constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(NewAccountDTO newAccountDTO, ConstraintValidatorContext context) {

        List<FieldErrorMessage> errorMessages = new ArrayList<>();

        if (newAccountDTO.email() == null) {
            errorMessages.add(new FieldErrorMessage(
                    "email",
                    "Email must not be null."
            ));
        }

        if (newAccountDTO.username() == null || !newAccountDTO.username().matches(USERNAME_POLICY)) {
            errorMessages.add(new FieldErrorMessage(
                    "username",
                    "Username must only contain letters, numbers, hyphens and dashes."
            ));
        }

        if (newAccountDTO.password() == null || !newAccountDTO.password().matches(PASSWORD_POLICY)) {
            errorMessages.add(new FieldErrorMessage(
                    "password",
                    "Password must be at least 8 characters long, " +
                            "contain at least one digit, " +
                            "one lowercase letter, " +
                            "and one uppercase letter."
            ));
        }

        if (errorMessages.isEmpty() && repository.existsAccountByEmail(newAccountDTO.email()))
            errorMessages.add(new FieldErrorMessage("email", "already in use."));

        if (errorMessages.isEmpty() && repository.existsAccountByUsername(newAccountDTO.username()))
            errorMessages.add(new FieldErrorMessage("username", "already in use."));

        errorMessages.forEach(fieldErrorMessage -> {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(fieldErrorMessage.getMessage())
                    .addPropertyNode(fieldErrorMessage.getFieldName())
                    .addConstraintViolation();
        });

        return errorMessages.isEmpty();
    }
}
