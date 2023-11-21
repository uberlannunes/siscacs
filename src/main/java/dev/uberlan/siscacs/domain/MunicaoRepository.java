package dev.uberlan.siscacs.domain;

import dev.uberlan.siscacs.domain.dto.MunicaoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface MunicaoRepository extends JpaRepository<Municao, UUID> {

    Municao findByArma(Arma arma);

    @Query("""
            SELECT
             new dev.uberlan.siscacs.domain.dto.MunicaoDTO(a.id, new dev.uberlan.siscacs.domain.dto.ArmaDTO(a.arma.id, a.arma.calibre, a.arma.descricao), a.quantidade)
            FROM Municao a
            WHERE a.id = ?1
            """)
    Optional<MunicaoDTO> findMunicaoById(UUID id);

    @Query("""
            SELECT
             new dev.uberlan.siscacs.domain.dto.MunicaoDTO(a.id, new dev.uberlan.siscacs.domain.dto.ArmaDTO(a.arma.id, a.arma.calibre, a.arma.descricao), a.quantidade)
            FROM Municao a
            WHERE a.arma = ?1
            """)
    Optional<MunicaoDTO> findMunicaoByArma(Arma arma);

    @Query("""
            SELECT
             new dev.uberlan.siscacs.domain.dto.MunicaoDTO(a.id, new dev.uberlan.siscacs.domain.dto.ArmaDTO(a.arma.id, a.arma.calibre, a.arma.descricao), a.quantidade)
            FROM Municao a
            WHERE EXISTS (
                           SELECT 1
                           FROM Arma b
                           WHERE b.id = a.arma.id
                             AND b.usuario = ?1
                          )
            ORDER BY a.arma
            """)
    List<MunicaoDTO> findMunicoesByUsuarioId(Usuario usuario);

    @Modifying
    @Query("update Municao m set m.quantidade = :quantidade where m.id = :id")
    void updateQuantidade(@Param(value = "id") UUID id, @Param(value = "quantidade") Integer quantidade);
}
