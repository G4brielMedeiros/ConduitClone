package dev.gabriel.conduitapi.service;

import dev.gabriel.conduitapi.domain.Account;
import dev.gabriel.conduitapi.dto.account.NewAccountDTO;
import dev.gabriel.conduitapi.dto.account.ProfileDTO;
import dev.gabriel.conduitapi.dto.account.UpdateAccountDTO;
import dev.gabriel.conduitapi.facade.AuthFacade;
import dev.gabriel.conduitapi.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

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
        var currentAccount = getCurrentAccount();

        if (dto.email() != null)
            currentAccount.setEmail(dto.email());

        if (dto.bio() != null)
            currentAccount.setBio(dto.bio());

        if (dto.image() != null)
            currentAccount.setImage(dto.image());

        return accountRepository.save(currentAccount);

    }

    public Account getCurrentAccount() {
        return accountRepository.findAccountByUsername(authFacade.getAuthentication().getName())
                .orElseThrow(() -> new BadCredentialsException("Could not find currently signed-in account"));
    }

    public boolean isCurrentUserAnon() {
        return authFacade.getAuthentication().getName().equals("anonymousUser");
    }

    public Account getAccountByUsername(String username) {
        return accountRepository.findAccountByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Account with username '%s' not found", username)));
    }

    public ProfileDTO getProfileByUsername(String username) {
        Account account = getAccountByUsername(username);
        return getProfileByAccount(account);
    }

    public ProfileDTO getProfileByAccount(Account account) {
        return isCurrentUserAnon() ?
                new ProfileDTO(account, false) :
                new ProfileDTO(account, getCurrentAccount().getFollowing().contains(account));
    }

    public boolean followAccount(Account accountToFollow) {
        Account currentAccount = getCurrentAccount();
        accountToFollow.getFollowers().add(currentAccount);
        return accountRepository.save(accountToFollow).getFollowers().contains(currentAccount);
    }

    public boolean unfollowAccount(Account accountToFollow) {
        Account currentAccount = getCurrentAccount();
        accountToFollow.getFollowers().remove(currentAccount);
        return accountRepository.save(accountToFollow).getFollowers().contains(currentAccount);
    }
}