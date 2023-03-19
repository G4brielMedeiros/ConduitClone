package dev.gabriel.conduitapi.service;

import dev.gabriel.conduitapi.domain.Account;
import dev.gabriel.conduitapi.dto.NewAccountDTO;
import dev.gabriel.conduitapi.dto.ProfileDTO;
import dev.gabriel.conduitapi.dto.UpdateAccountDTO;
import dev.gabriel.conduitapi.facade.AuthFacade;
import dev.gabriel.conduitapi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthFacade authFacade;

    public Account addAccount(NewAccountDTO dto) {
        var account = new Account(
                dto.username(), dto.email(),
                passwordEncoder.encode(dto.password())
        );
        return accountRepository.save(account);
    }

    public Account updateAccount(UpdateAccountDTO dto) {
        var currentAccount = getCurrentAccount().orElseThrow(
                () -> new EntityNotFoundException("Could not find currently signed-in Account.")
        );

        if (dto.email() != null)
            currentAccount.setEmail(dto.email());

        if (dto.bio() != null)
            currentAccount.setBio(dto.bio());

        if (dto.image() != null)
            currentAccount.setImage(dto.image());

        return accountRepository.save(currentAccount);

    }

    public Optional<Account> getCurrentAccount() {
        var auth = authFacade.getAuthentication();
        return accountRepository.findAccountByUsername(auth.getName());
    }

    public Optional<ProfileDTO> getProfileByUsername(String username) {
        // TODO: 29/01/2023 implement following
        return accountRepository.findAccountByUsername(username)
                .map(account -> new ProfileDTO(account, false));
    }

}
