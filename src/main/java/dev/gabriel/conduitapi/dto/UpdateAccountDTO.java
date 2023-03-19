package dev.gabriel.conduitapi.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public record UpdateAccountDTO(

        @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
        @Length(max = 255)
        String email,

        @Length(max = 255)
        String bio,

        @Length(max = 255)
        String image
) {
}
