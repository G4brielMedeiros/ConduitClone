package dev.gabriel.conduitapi.domain;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    @Column(nullable = false)
    String body;

    @NonNull
    @Column(nullable = false)
    Instant createdAt;

    @Setter(AccessLevel.NONE)
    Instant updatedAt;

}
