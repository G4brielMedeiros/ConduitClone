package dev.gabriel.conduitapi.repository;

import dev.gabriel.conduitapi.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    public Optional<Tag> findTagByTagValue(String tagValue);
}
