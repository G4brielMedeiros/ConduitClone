package dev.gabriel.conduitapi.dto;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.gabriel.conduitapi.domain.Account;

@JsonTypeName("profile")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public record ProfileDTO(String username, String bio, String image, boolean following) {
    public ProfileDTO(Account account, boolean following) {
        this(account.getUsername(), account.getBio(), account.getImage(), following);
    }
}
