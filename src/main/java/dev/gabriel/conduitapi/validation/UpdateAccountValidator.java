package dev.gabriel.conduitapi.validation;

import dev.gabriel.conduitapi.dto.UpdateAccountDTO;
import dev.gabriel.conduitapi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UpdateAccountValidator implements ConstraintValidator<ValidUpdateAccount, UpdateAccountDTO> {

    private final AccountRepository accountRepository;

    @Override
    public void initialize(ValidUpdateAccount constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UpdateAccountDTO updateAccountDTO, ConstraintValidatorContext context) {

        if (!accountRepository.existsAccountByEmail(updateAccountDTO.email()))
            return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("already in use")
                .addPropertyNode("email")
                .addConstraintViolation();

        return false;
    }
}
