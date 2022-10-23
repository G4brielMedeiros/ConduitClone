package dev.gabriel.conduitapi.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @NonNull
    @Column(unique = true, nullable = false)
    String username;

    @NonNull
    @Column(unique = true, nullable = false)
    String email;

    @NonNull
    @Column(nullable = false)
    String password;

    String bio;

}
