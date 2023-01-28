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

    final AccountRepository repository;

    @Override
    public void initialize(ValidNewAccount constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(NewAccountDTO newAccountDTO, ConstraintValidatorContext context) {

        List<FieldErrorMessage> errorMessages = new ArrayList<>();

        repository.findAccountByEmail(newAccountDTO.email()).ifPresent(account ->
                errorMessages.add(new FieldErrorMessage("email", "Email already in use."))
        );

        repository.findAccountByUsername(newAccountDTO.username()).ifPresent(account ->
                errorMessages.add(new FieldErrorMessage("username", "Username already in use."))
        );

        errorMessages.forEach(fieldErrorMessage -> {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(fieldErrorMessage.getMessage())
                    .addPropertyNode(fieldErrorMessage.getFieldName())
                    .addConstraintViolation();
        });

        return errorMessages.isEmpty();

    }
}
