package dev.gabriel.conduitapi.controller;

import dev.gabriel.conduitapi.dto.ProfileDTO;
import dev.gabriel.conduitapi.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    private final AccountService accountService;

    @GetMapping("profiles/{username}")
    public ResponseEntity<ProfileDTO> getProfile(@PathVariable String username) {
        return accountService.getProfileByUsername(username)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("profiles/{username}/follow")
    public ResponseEntity<ProfileDTO> followProfile(@PathVariable String username) {
        return null;
    }
}
