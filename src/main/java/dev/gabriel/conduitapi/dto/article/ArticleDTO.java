package dev.gabriel.conduitapi.dto.article;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.gabriel.conduitapi.domain.Article;
import dev.gabriel.conduitapi.domain.Tag;
import dev.gabriel.conduitapi.dto.account.ProfileDTO;

import java.time.Instant;
import java.util.Set;
import java.util.stream.Collectors;

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
) {
    public static ArticleDTO from(Article article, boolean following, boolean favorited, int favoritesCount) {
        return new ArticleDTO(
                article.getSlug(),
                article.getTitle(),
                article.getDescription(),
                article.getBody(),
                article.getTags().stream().map(Tag::getTagValue).collect(Collectors.toSet()),
                article.getCreatedAt(),
                article.getUpdatedAt(),
                favorited,
                favoritesCount,
                ProfileDTO.from(article.getAuthor(), following)
        );
    }
}
