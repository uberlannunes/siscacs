package dev.uberlan.siscacs.domain;

import dev.uberlan.siscacs.domain.dto.ArmaDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface ArmaRepository extends JpaRepository<Arma, UUID> {

    @Query("""
            SELECT
             new dev.uberlan.siscacs.domain.dto.ArmaDTO(a.id, a.calibre, a.descricao, a.dataCompra, a.dataVenda, a.usuario.id, a.observacao)
            FROM Arma a
            WHERE a.id = ?1
            """)
    Optional<ArmaDTO> findArmaById(UUID id);

    @Query("""
            SELECT
             new dev.uberlan.siscacs.domain.dto.ArmaDTO(a.id, a.calibre, a.descricao, a.dataCompra, a.dataVenda, a.usuario.id, a.observacao)
            FROM Arma a
            WHERE a.usuario = ?1
            """)
    List<ArmaDTO> findArmaByUsuarioId(Usuario usuario);
}
