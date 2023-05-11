package dev.gabriel.conduitapi.repository;

import dev.gabriel.conduitapi.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    Optional<Article> findArticleBySlug(String slug);

    boolean existsArticleBySlug(String slug);

}
