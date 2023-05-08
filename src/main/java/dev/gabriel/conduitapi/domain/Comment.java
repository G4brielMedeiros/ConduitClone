package dev.gabriel.conduitapi.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(nullable = false)
    private String body;

    @NonNull
    @Column(nullable = false)
    private Instant createdAt;

    @Setter(AccessLevel.NONE)
    private Instant updatedAt;

}
