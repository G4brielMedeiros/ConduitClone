package dev.gabriel.conduitapi.validation;

import dev.gabriel.conduitapi.dto.account.UpdateAccountDTO;
import dev.gabriel.conduitapi.repository.AccountRepository;
import dev.gabriel.conduitapi.service.AccountService;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class UpdateAccountValidator implements ConstraintValidator<ValidUpdateAccount, UpdateAccountDTO> {

    private final AccountRepository accountRepository;
    private final AccountService accountService;

    @Override
    public void initialize(ValidUpdateAccount constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UpdateAccountDTO updateAccountDTO, ConstraintValidatorContext context) {

        if (!accountRepository.existsAccountByEmail(updateAccountDTO.email())) return true;

        if (accountService.getCurrentAccount().getEmail().equals(updateAccountDTO.email())) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("already in use")
                .addPropertyNode("email")
                .addConstraintViolation();

        return false;
    }
}
