package dev.gabriel.conduitapi.service;

import dev.gabriel.conduitapi.domain.Account;
import dev.gabriel.conduitapi.domain.Article;
import dev.gabriel.conduitapi.domain.Tag;
import dev.gabriel.conduitapi.dto.article.NewArticleDTO;
import dev.gabriel.conduitapi.repository.ArticleRepository;
import dev.gabriel.conduitapi.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
                .title(dto.title().trim())
                .body(dto.body())
                .description(dto.description())
                .createdAt(now)
                .updatedAt(now)
                .slug(toSlug(dto.title().trim()))
                .author(accountService.getCurrentAccount())
                .favoriteAccounts(Set.of())
                .tags(dto.tagList().stream()
                        .map(tagValue -> tagRepository
                                .findTagByTagValue(tagValue)
                                .orElseGet(() -> tagRepository.save(new Tag(tagValue.trim()))))
                        .collect(Collectors.toSet()))
                .build()
        );
    }

    public void deleteArticleBySlug(String slug) {
        Account currentAccount = accountService.getCurrentAccount();
        Article article = getArticleBySlug(slug);

        if (article.getAuthor().equals(currentAccount))
            articleRepository.delete(getArticleBySlug(slug));
        else
            throw new AccessDeniedException(String.format("Account '%s' is not allowed to delete Article '%s'",
                    currentAccount.getUsername(), slug));
    }

    public Article getArticleBySlug(String slug) {
        return articleRepository.findArticleBySlug(slug)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Article with slug '%s' not found", slug)));
    }

    public Article favoriteArticle(String slug) {
        Article article = articleRepository.findArticleBySlug(slug)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Article with slug '%s' not found", slug)));

        article.getFavoriteAccounts().add(accountService.getCurrentAccount());
        return articleRepository.save(article);
    }

    public Article unfavoriteArticle(String slug) {
        Article article = articleRepository.findArticleBySlug(slug)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Article with slug '%s' not found", slug)));

        article.getFavoriteAccounts().remove(accountService.getCurrentAccount());
        return articleRepository.save(article);
    }

    private String toSlug(String title) {
        String slug = title
                .toLowerCase()
                .replaceAll("[^a-z0-9 ]", "")
                .replace(" ", "_");

        return articleRepository.existsArticleBySlug(slug) ? toSlugWithSuffix(slug, makeSlugSuffix()) : slug;
    }

    private String toSlugWithSuffix(String preSlug, String suffix) {
        String slug = preSlug + suffix;
        return articleRepository.existsArticleBySlug(slug) ? toSlugWithSuffix(preSlug, makeSlugSuffix()) : slug;
    }

    private static String makeSlugSuffix() {
        String chars = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder("__");
        IntStream.range(0, 10).forEach(i -> sb.append(chars.charAt(random.nextInt(chars.length()))));
        return sb.toString();
    }


}
