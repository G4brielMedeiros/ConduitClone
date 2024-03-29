package dev.gabriel.conduitapi.controller;

import dev.gabriel.conduitapi.domain.Account;
import dev.gabriel.conduitapi.domain.Article;
import dev.gabriel.conduitapi.dto.article.ArticleDTO;
import dev.gabriel.conduitapi.dto.article.NewArticleDTO;
import dev.gabriel.conduitapi.service.AccountService;
import dev.gabriel.conduitapi.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor
@RestController
@RequestMapping("articles")
public class ArticleController {

    private final ArticleService articleService;
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@Valid @RequestBody NewArticleDTO dto) {
        return ok(getArticleDTO(articleService.createArticle(dto)));
    }

    @GetMapping("{slug}")
    public ResponseEntity<ArticleDTO> fetchArticleBySlug(@PathVariable String slug) {
        return ok(getArticleDTO(articleService.getArticleBySlug(slug)));
    }

    @DeleteMapping("{slug}")
    public void deleteArticleBySlug(@PathVariable String slug) {
        articleService.deleteArticleBySlug(slug);
    }

    @PostMapping("{slug}/favorite")
    public ArticleDTO favoriteArticleBySlug(@PathVariable String slug) {
        return getArticleDTO(articleService.favoriteArticle(slug));
    }

    @DeleteMapping("{slug}/favorite")
    public ArticleDTO unfavoriteArticleBySlug(@PathVariable String slug) {
        return getArticleDTO(articleService.unfavoriteArticle(slug));
    }

    private ArticleDTO getArticleDTO(Article article) {
        try {
            Account currentAccount = accountService.getCurrentAccount();
            boolean isArticleFavorited = article.getFavoriteAccounts().contains(currentAccount);
            boolean isAuthorFollowed = accountService.isAccountFollowing(currentAccount, article.getAuthor());
            return ArticleDTO.from(article, isAuthorFollowed, isArticleFavorited);

        } catch (BadCredentialsException e) {
            return ArticleDTO.from(article);
        }
    }
}
