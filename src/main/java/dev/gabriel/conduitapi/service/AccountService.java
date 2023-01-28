package dev.gabriel.conduitapi.service;

import dev.gabriel.conduitapi.domain.Account;
import dev.gabriel.conduitapi.dto.AuthUserDTO;
import dev.gabriel.conduitapi.dto.NewAccountDTO;
import dev.gabriel.conduitapi.repository.AccountRepository;
import dev.gabriel.conduitapi.service.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {

    final AccountRepository repository;
    final PasswordEncoder passwordEncoder;
    final TokenService tokenService;

    public Account addAccount(NewAccountDTO dto) {
        var account = new Account(
                dto.username(),
                dto.email(),
                passwordEncoder.encode(dto.password())
        );
        return repository.save(account);
    }

    public AuthUserDTO getCurrentUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var account = repository.findAccountByEmail(auth.getName()).orElseThrow();
        return new AuthUserDTO(auth.getName(), tokenService.generateToken(auth), account.getUsername(), account.getBio());
    }

}
