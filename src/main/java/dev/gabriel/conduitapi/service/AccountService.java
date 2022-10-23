package dev.gabriel.conduitapi.service;

import dev.gabriel.conduitapi.domain.Account;
import dev.gabriel.conduitapi.dto.NewAccountDTO;
import dev.gabriel.conduitapi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AccountService {

    final AccountRepository repository;
    final PasswordEncoder passwordEncoder;

    public Account addAccount(NewAccountDTO dto) {
        var account = new Account(
                dto.username(),
                dto.email(),
                passwordEncoder.encode(dto.password())
        );
        return repository.save(account);
    }

}
