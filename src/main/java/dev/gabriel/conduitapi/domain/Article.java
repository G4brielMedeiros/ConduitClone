package dev.gabriel.conduitapi.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false, unique = true)
    private String slug;

    @NonNull
    @Column(nullable = false)
    private String title;

    @NonNull
    @Column(nullable = false)
    private String description;

    @NonNull
    @Column(nullable = false)
    private String body;

    @NonNull
    @Column(nullable = false)
    Instant createdAt;

    @Setter(AccessLevel.NONE)
    Instant updatedAt;

    @ManyToOne
    private Account author;


    @ManyToMany
    @JoinTable(name = "tagged_article",
            joinColumns =
            @JoinColumn(name = "article_id", referencedColumnName = "id",
                    nullable = false, updatable = false),
            inverseJoinColumns =
            @JoinColumn(name = "tag_id", referencedColumnName = "id",
                    nullable = false, updatable = false))
    private Set<Tag> tags = new HashSet<>();

    public void removeTagById(Long tagId) {
        setTags(tags.stream().filter(tag -> !tag.getId().equals(tagId)).collect(Collectors.toSet()));
    }

}
