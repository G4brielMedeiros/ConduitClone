package dev.gabriel.conduitapi.controller;

import dev.gabriel.conduitapi.domain.Tag;
import dev.gabriel.conduitapi.dto.TagDTO;
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
    public ResponseEntity<Tag> createTag(@RequestBody TagDTO dto) {
        var tag = service.createTag(dto);
        return ResponseEntity.ok(tag);
    }

    @GetMapping
    public ResponseEntity<TagListDTO> getAllTags() {
        var tagListDTO = new TagListDTO(service.getAllTagValues());
        return ResponseEntity.ok(tagListDTO);
    }

    private record TagListDTO(Set<String> tags) {
    }
}
