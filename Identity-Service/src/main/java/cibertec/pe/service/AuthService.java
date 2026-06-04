package cibertec.pe.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cibertec.pe.config.JwtUtil;
import cibertec.pe.dto.AuthRequest;
import cibertec.pe.dto.AuthResponse;
import cibertec.pe.dto.RegisterRequest;
import cibertec.pe.exception.ResourceNotFoundException;
import cibertec.pe.model.UsuarioCredential;
import cibertec.pe.repository.UsuarioCredentialRepository;

@Service
@Transactional
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UsuarioCredentialRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UsuarioCredentialRepository repository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterRequest request) {
        log.info("Registering user: {}", request.getEmail());

        if (repository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered: " + request.getEmail());
        }

        UsuarioCredential usuario = new UsuarioCredential(
                request.getName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()));

        UsuarioCredential saved = repository.save(usuario);
        log.info("User registered with id: {}", saved.getId());

        String token = jwtUtil.generateToken(saved.getEmail());
        return new AuthResponse(token, saved.getEmail(), saved.getName(), jwtUtil.getExpiration());
    }

    public AuthResponse login(AuthRequest request) {
        log.info("Login attempt for: {}", request.getEmail());

        UsuarioCredential usuario = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            throw new BadCredentialsException("Invalid email or password");
        }

        log.info("User logged in: {}", usuario.getEmail());

        String token = jwtUtil.generateToken(usuario.getEmail());
        return new AuthResponse(token, usuario.getEmail(), usuario.getName(), jwtUtil.getExpiration());
    }

    public AuthResponse validateToken(String token) {
        log.info("Validating token");

        if (!jwtUtil.validateToken(token)) {
            throw new BadCredentialsException("Invalid or expired token");
        }

        String email = jwtUtil.getEmailFromToken(token);
        UsuarioCredential usuario = repository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

        return new AuthResponse(token, usuario.getEmail(), usuario.getName(), jwtUtil.getExpiration());
    }
}
