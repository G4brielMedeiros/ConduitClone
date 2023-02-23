package dev.gabriel.conduitapi.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.gabriel.conduitapi.validation.ValidNewAccount;

import javax.validation.constraints.Email;

@ValidNewAccount
@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public record NewAccountDTO(String username, @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$") String email, String password) {
}
