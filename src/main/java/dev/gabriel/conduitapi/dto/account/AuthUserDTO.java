package dev.gabriel.conduitapi.dto.account;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.gabriel.conduitapi.domain.Account;

@JsonTypeName("user")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public record AuthUserDTO(String email, String token, String username, String bio, String image) {

    public AuthUserDTO(Account account, String token) {
        this(account.getEmail(), token, account.getUsername(), account.getBio(), account.getImage());
    }

}
