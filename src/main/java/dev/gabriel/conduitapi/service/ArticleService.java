package dev.gabriel.conduitapi.service;

import dev.gabriel.conduitapi.domain.Article;
import dev.gabriel.conduitapi.domain.Tag;
import dev.gabriel.conduitapi.dto.article.NewArticleDTO;
import dev.gabriel.conduitapi.repository.ArticleRepository;
import dev.gabriel.conduitapi.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final AccountService accountService;

    private static final Random random = new Random();

    public Article createArticle(NewArticleDTO dto) {

        Instant now = Instant.now();

        return articleRepository.save(Article.builder()
                .title(dto.title())
                .body(dto.body())
                .description(dto.description())
                .createdAt(now)
                .updatedAt(now)
                .slug(toSlug(dto.title(), ""))
                .author(accountService.getCurrentAccount())
                .tags(dto.tagList().stream()
                        .map(tagValue -> tagRepository
                                .findTagByTagValue(tagValue)
                                .orElseGet(() -> tagRepository.save(new Tag(tagValue))))
                        .collect(Collectors.toSet()))
                .build()
        );
    }

    public Article getArticleBySlug(String slug) {
        return articleRepository.findArticleBySlug(slug)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Article with slug '%s' not found", slug)));
    }

    private String toSlug(String title, String suffix) {
        String slug = title
                .toLowerCase()
                .replaceAll("[^a-z0-9 ]", "")
                .replace(" ", "_")
                + suffix;

        return articleRepository.existsArticleBySlug(slug) ? toSlug(title, makeSlugSuffix()) : slug;
    }

    private static String makeSlugSuffix() {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder("-");
        IntStream.range(0, 10).forEach(i -> sb.append(chars.charAt(random.nextInt(chars.length()))));
        return sb.toString();
    }


}
