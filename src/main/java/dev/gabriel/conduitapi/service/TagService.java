package dev.gabriel.conduitapi.service;

import dev.gabriel.conduitapi.domain.Tag;
import dev.gabriel.conduitapi.dto.NewTagDTO;
import dev.gabriel.conduitapi.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TagService {

    final TagRepository repository;

    public Set<String> getAllTagValues() {
        return repository.findAll().stream().map(Tag::getTagValue).collect(Collectors.toSet());
    }

    public Tag createTag(NewTagDTO dto) {
        return repository.save(new Tag(dto.tagValue()));
    }

    public Optional<Tag> findTagById(Long id) {
        return repository.findById(id);
    }

    public void deleteTagById(Long tagId) {

        findTagById(tagId).ifPresent(tag -> {
            tag.getArticles().forEach(article -> article.removeTagById(tagId));
            repository.delete(tag);
        });

    }
}
