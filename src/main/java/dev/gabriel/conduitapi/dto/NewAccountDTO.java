package dev.gabriel.conduitapi.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.gabriel.conduitapi.validation.ValidNewAccount;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@ValidNewAccount
@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public record NewAccountDTO(

        @Length(min = 8, max = 32)
        String username,

        @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        @Length(max = 255)
        String email,

        @Length(min = 8, max = 255)
        String password
) {
}
