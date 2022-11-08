package dev.gabriel.conduitapi.service;

import dev.gabriel.conduitapi.domain.Tag;
import dev.gabriel.conduitapi.dto.TagDTO;
import dev.gabriel.conduitapi.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TagService {

    final TagRepository repository;

    public Set<String> getAllTagValues() {
        return repository.findAll().stream().map(Tag::getTagValue).collect(Collectors.toSet());
    }

    public Tag createTag(TagDTO dto) {
        return repository.save(new Tag(dto.tagValue()));
    }

    public Tag findTagById(Long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void deleteTagById(Long tagId) {
        var tag = findTagById(tagId);
        tag.getArticles().forEach(article -> article.removeTagById(tagId));
        repository.delete(tag);
    }
}
