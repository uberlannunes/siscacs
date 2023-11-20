package dev.uberlan.siscacs.domain;

import dev.uberlan.siscacs.domain.dto.UsuarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    @Query("""
            SELECT
             new dev.uberlan.siscacs.domain.dto.UsuarioDTO(u.id, u.login, u.nome, u.cacId)
            FROM Usuario u
            WHERE u.id = ?1
            """)
    Optional<UsuarioDTO> findUsuarioById(UUID id);

    @Query("""
            SELECT
             new dev.uberlan.siscacs.domain.dto.UsuarioDTO(u.id, u.login, u.nome, u.cacId)
            FROM Usuario u
            """)
    List<UsuarioDTO> findAllUsuarios();

    @Modifying
    @Query("update Usuario u set u.password = :password where u.id = :id")
    void updatePassword(@Param(value = "id") UUID id, @Param(value = "password") String password);

    Usuario findByLogin(String login);
}
