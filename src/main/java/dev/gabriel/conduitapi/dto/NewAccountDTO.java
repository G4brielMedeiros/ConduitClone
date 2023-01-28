package dev.gabriel.conduitapi.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.gabriel.conduitapi.validation.ValidNewAccount;

@ValidNewAccount
@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public record NewAccountDTO(String username, String email, String password) {
}
