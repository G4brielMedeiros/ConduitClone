package dev.gabriel.conduitapi.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.NonNull;

import javax.persistence.Lob;
import java.util.Set;

@JsonTypeName("article")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public record NewArticleDTO(

        @NonNull
        @Length(min = 1, max = 100)
        String title,

        @NonNull
        @Length(min = 1, max = 500)
        String description,

        @NonNull
        @Lob
        @Length(min = 1, max = 3000)
        String body,

        Set<String> tagList
) {}
