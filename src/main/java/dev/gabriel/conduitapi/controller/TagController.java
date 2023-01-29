package dev.gabriel.conduitapi.controller;

import dev.gabriel.conduitapi.domain.Tag;
import dev.gabriel.conduitapi.dto.NewTagDTO;
import dev.gabriel.conduitapi.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "tags")
public class TagController {

    final TagService service;

    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody NewTagDTO dto) {
        var tag = service.createTag(dto);
        return ResponseEntity.ok(tag);
    }

    @GetMapping
    public ResponseEntity<TagListDTO> getAllTags() {
        return ResponseEntity.ok(new TagListDTO(service.getAllTagValues()));
    }

    @GetMapping("{tagId}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long tagId) {
        return service.findTagById(tagId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{tagId}")
    public ResponseEntity<Object> deleteTag(@PathVariable Long tagId) {
        service.deleteTagById(tagId);
        return ResponseEntity.ok().build();
    }

    private record TagListDTO(Set<String> tags) {
    }
}
