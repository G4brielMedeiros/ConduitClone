package dev.gabriel.conduitapi.dto.article;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.gabriel.conduitapi.dto.account.ProfileDTO;
import lombok.Builder;

import java.time.Instant;
import java.util.Set;

@Builder
@JsonTypeName("article")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public record ArticleDTO(
        String slug,
        String title,
        String description,
        String body,
        Set<String> tagList,
        Instant createdAt,
        Instant updatedAt,
        boolean favorited,
        int favoritesCount,
        ProfileDTO author

) {}
