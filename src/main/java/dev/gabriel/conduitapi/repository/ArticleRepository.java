package dev.gabriel.conduitapi.repository;

import dev.gabriel.conduitapi.domain.Article;
import dev.gabriel.conduitapi.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
