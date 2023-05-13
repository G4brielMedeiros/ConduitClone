package dev.gabriel.conduitapi.dto.account;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dev.gabriel.conduitapi.domain.Account;

@JsonTypeName("profile")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public record ProfileDTO(String username, String bio, String image, boolean following) {
    public static ProfileDTO from(Account account, boolean following) {
        return new ProfileDTO(account.getUsername(), account.getBio(), account.getImage(), following);
    }
}
