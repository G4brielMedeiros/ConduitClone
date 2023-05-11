package dev.gabriel.conduitapi.controller;

import dev.gabriel.conduitapi.domain.Account;
import dev.gabriel.conduitapi.dto.ProfileDTO;
import dev.gabriel.conduitapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final AccountService accountService;

    @GetMapping("profiles/{username}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable String username) {
        return ok(accountService.getProfileByUsername(username));
    }

    @PostMapping("profiles/{username}/follow")
    public ResponseEntity<ProfileDTO> followProfile(@PathVariable String username) {

        Account accountToFollow = accountService.getAccountByUsername(username);
        boolean following = accountService.followAccount(accountToFollow);
        return ok(new ProfileDTO(accountToFollow, following));
    }

    @DeleteMapping("profiles/{username}/follow")
    public ResponseEntity<ProfileDTO> unfollowProfile(@PathVariable String username) {

        Account accountToUnfollow = accountService.getAccountByUsername(username);
        boolean following = accountService.unfollowAccount(accountToUnfollow);
        return ok(new ProfileDTO(accountToUnfollow, following));
    }
}