package dev.gabriel.conduitapi.service.security;

import dev.gabriel.conduitapi.repository.AccountRepository;
import dev.gabriel.conduitapi.security.UserSS;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    final AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var account = repository.findAccountByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(email));

        return new UserSS(account);
    }
}
