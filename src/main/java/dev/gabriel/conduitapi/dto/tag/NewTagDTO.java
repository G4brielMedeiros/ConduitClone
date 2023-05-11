package dev.gabriel.conduitapi.dto.tag;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public record NewTagDTO(

        @NotBlank
        @Length(min = 1, max = 50)
        String tagValue
) {
}