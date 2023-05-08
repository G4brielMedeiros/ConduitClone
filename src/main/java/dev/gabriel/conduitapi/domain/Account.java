package dev.gabriel.conduitapi.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(unique = true, nullable = false)
    private String username;

    @NonNull
    @Column(unique = true, nullable = false)
    private String email;

    @NonNull
    @Column(nullable = false)
    private String password;

    private String bio;

    private String image;

    @ManyToMany
    @JoinTable(
            name = "account_followers",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    private List<Account> followers = new ArrayList<>();

    @ManyToMany(mappedBy = "followers")
    private List<Account> following = new ArrayList<>();

}
