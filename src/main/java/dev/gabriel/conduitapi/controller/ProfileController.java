package dev.gabriel.conduitapi.controller;

import dev.gabriel.conduitapi.domain.Account;
import dev.gabriel.conduitapi.dto.ProfileDTO;
import dev.gabriel.conduitapi.repository.AccountRepository;
import dev.gabriel.conduitapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @GetMapping("profiles/{username}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable String username) {
        return accountService.getProfileByUsername(username)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("profiles/{username}/follow")
    public ResponseEntity<ProfileDTO> followProfile(@PathVariable String username) {

        Account accountToFollow = accountRepository.findAccountByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Account with username %s not found", username)));

        accountService.followAccount(accountToFollow);

        return ResponseEntity.ok(new ProfileDTO(accountToFollow, true));
    }

    @DeleteMapping("profiles/{username}/follow")
    public ResponseEntity<ProfileDTO> unfollowProfile(@PathVariable String username) {

        Account accountToUnfollow = accountRepository.findAccountByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Account with username %s not found", username)));

        accountService.unfollowAccount(accountToUnfollow);

        return ResponseEntity.ok(new ProfileDTO(accountToUnfollow, false));
    }
}
