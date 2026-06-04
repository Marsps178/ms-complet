package cibertec.pe.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import cibertec.pe.model.UsuarioCredential;
import cibertec.pe.repository.UsuarioCredentialRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioCredentialRepository repository;

    public CustomUserDetailsService(UsuarioCredentialRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UsuarioCredential usuario = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new User(usuario.getEmail(), usuario.getPassword(), Collections.emptyList());
    }
}
