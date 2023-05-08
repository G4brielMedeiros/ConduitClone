package dev.gabriel.conduitapi.config;

import dev.gabriel.conduitapi.domain.Account;
import dev.gabriel.conduitapi.domain.Article;
import dev.gabriel.conduitapi.domain.Tag;
import dev.gabriel.conduitapi.repository.AccountRepository;
import dev.gabriel.conduitapi.repository.ArticleRepository;
import dev.gabriel.conduitapi.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DatabaseConfig {

    private final AccountRepository accountRepository;
    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;
    private final PasswordEncoder passwordEncoder;

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


        var gabriel = new Account();
        gabriel.setBio("The Creator");
        gabriel.setUsername("Creator");
        gabriel.setEmail("gabriel@gmail.com");
        gabriel.setPassword(passwordEncoder.encode("Password123#"));

        var lethicia = new Account();
        lethicia.setBio("The Ducky");
        lethicia.setUsername("Ducky");
        lethicia.setEmail("lethicia@gmail.com");
        lethicia.setPassword(passwordEncoder.encode("Password123#"));

        accountRepository.saveAll(Set.of(gabriel, lethicia));

        return true;
    }
}
