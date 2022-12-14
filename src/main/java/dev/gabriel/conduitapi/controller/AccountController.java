package dev.gabriel.conduitapi.controller;

import dev.gabriel.conduitapi.dto.AuthUserDTO;
import dev.gabriel.conduitapi.dto.LoginUserDTO;
import dev.gabriel.conduitapi.dto.NewAccountDTO;
import dev.gabriel.conduitapi.security.UserSS;
import dev.gabriel.conduitapi.service.AccountService;
import dev.gabriel.conduitapi.service.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RestController
public class AccountController {

    final AccountService service;
    final TokenService tokenService;
    final AuthenticationManager authenticationManager;


    @PostMapping("users")
    public ResponseEntity<AuthUserDTO> createAccount(@RequestBody NewAccountDTO newAccountDTO) {
        var account = service.addAccount(newAccountDTO);

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        newAccountDTO.email(), newAccountDTO.password()
                )
        );

        var token = tokenService.generateToken(auth);

        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(account.getId()).toUri();

        var authUserDTO = new AuthUserDTO(newAccountDTO.email(), token, newAccountDTO.username(), null);

        return ResponseEntity.created(uri).body(authUserDTO);
    }

    @PostMapping("users/login")
    public ResponseEntity<AuthUserDTO> login(@RequestBody LoginUserDTO loginUserDTO) {

        var auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUserDTO.email(), loginUserDTO.password()
                )
        );

        var account = ((UserSS) auth.getPrincipal()).getAccount();

        var token = tokenService.generateToken(auth);

        var authUserDTO = new AuthUserDTO(account.getEmail(), token, account.getUsername(), account.getBio());

        return ResponseEntity.ok(authUserDTO);
    }

    @GetMapping("user")
    public ResponseEntity<AuthUserDTO> getCurrentUser() {
        try {
            return ResponseEntity.ok(service.getCurrentUser());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
