package dev.gabriel.conduitapi.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
    private Set<Account> followers;

    @ManyToMany(mappedBy = "followers")
    private Set<Account> following;

    @JsonIgnore
    @ManyToMany( mappedBy = "favoriteAccounts", fetch = FetchType.LAZY)
    private Set<Article> favoriteArticles = new HashSet<>();
}