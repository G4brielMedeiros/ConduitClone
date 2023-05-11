package dev.gabriel.conduitapi.controller;

import dev.gabriel.conduitapi.domain.Article;
import dev.gabriel.conduitapi.domain.Tag;
import dev.gabriel.conduitapi.dto.article.ArticleDTO;
import dev.gabriel.conduitapi.dto.article.NewArticleDTO;
import dev.gabriel.conduitapi.service.AccountService;
import dev.gabriel.conduitapi.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "articles")
public class ArticleController {

    private final ArticleService articleService;
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@Valid @RequestBody NewArticleDTO dto) {
        Article article = articleService.createArticle(dto);

        ArticleDTO articleDTO = ArticleDTO.builder()
                .slug(article.getSlug())
                .title(article.getTitle())
                .description(article.getDescription())
                .body(article.getBody())
                .tagList(article.getTags().stream().map(Tag::getTagValue).collect(Collectors.toSet()))
                .createdAt(article.getCreatedAt())
                .updatedAt(article.getUpdatedAt())
                .author(accountService.getProfileByAccount(article.getAuthor()))
                .build();

        return ResponseEntity.ok(articleDTO);
    }
}
