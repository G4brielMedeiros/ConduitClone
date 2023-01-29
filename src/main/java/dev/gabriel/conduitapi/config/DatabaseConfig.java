package dev.gabriel.conduitapi.config;

import dev.gabriel.conduitapi.domain.Article;
import dev.gabriel.conduitapi.domain.Tag;
import dev.gabriel.conduitapi.repository.AccountRepository;
import dev.gabriel.conduitapi.repository.ArticleRepository;
import dev.gabriel.conduitapi.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {

    final AccountRepository accountRepository;
    final ArticleRepository articleRepository;
    final TagRepository tagRepository;

    @Bean
    public boolean instantiateDatabase() {

        var article = new Article(
                "this-is-a-test-article",
                "This is a Test Article",
                "i am testing stuff",
                "It all began with... The end!",
                Instant.now()
        );

        var article2 = new Article(
                "this-is-a-second-article",
                "This is a Second Article",
                "i am testing stuff",
                "It all starts with... The beginning!",
                Instant.now()
        );

        var tag = new Tag("Cool");
        var tag2 = new Tag("Informative");
        var tag3 = new Tag("Useless");


        article.setTags(Set.of(tag, tag2));
        article2.setTags(Set.of(tag2));

        tagRepository.saveAll(Set.of(tag, tag2, tag3));
        articleRepository.saveAll(Set.of(article, article2));

        return true;
    }
}
