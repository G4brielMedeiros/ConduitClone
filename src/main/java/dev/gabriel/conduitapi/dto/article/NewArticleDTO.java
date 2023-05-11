package dev.gabriel.conduitapi.dto.article;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@JsonTypeName("article")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public record NewArticleDTO(

        @NotBlank
        @Length(min = 1, max = 100)
        String title,

        @NotBlank
        @Length(min = 1, max = 500)
        String description,

        @NotBlank
        @Lob
        @Length(min = 1, max = 3000)
        String body,

        @Size(max = 30)
        Set<String> tagList
) {}
