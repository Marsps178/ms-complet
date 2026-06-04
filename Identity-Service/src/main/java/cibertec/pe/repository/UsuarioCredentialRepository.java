package cibertec.pe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cibertec.pe.model.UsuarioCredential;

@Repository
public interface UsuarioCredentialRepository extends JpaRepository<UsuarioCredential, Integer> {

    Optional<UsuarioCredential> findByEmail(String email);

    boolean existsByEmail(String email);
}
