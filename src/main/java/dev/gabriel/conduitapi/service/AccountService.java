package dev.gabriel.conduitapi.service;

import dev.gabriel.conduitapi.domain.Account;
import dev.gabriel.conduitapi.dto.AuthUserDTO;
import dev.gabriel.conduitapi.dto.NewAccountDTO;
import dev.gabriel.conduitapi.dto.ProfileDTO;
import dev.gabriel.conduitapi.repository.AccountRepository;
import dev.gabriel.conduitapi.service.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {

    final AccountRepository repository;
    final PasswordEncoder passwordEncoder;
    final TokenService tokenService;

    public Account addAccount(NewAccountDTO dto) {
        var account = new Account(
                dto.username(), dto.email(),
                passwordEncoder.encode(dto.password())
        );
        return repository.save(account);
    }

    public Optional<AuthUserDTO> getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return repository.findAccountByEmail(auth.getName())
                .map(account -> new AuthUserDTO(account, tokenService.generateToken(auth)));
    }

    public Optional<ProfileDTO> getProfileByUsername(String username) {
        // TODO: 29/01/2023 implement following
        return repository.findAccountByUsername(username)
                .map(account -> new ProfileDTO(account, false));
    }

}
