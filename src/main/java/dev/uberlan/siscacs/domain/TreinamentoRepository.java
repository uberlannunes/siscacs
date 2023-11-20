package dev.uberlan.siscacs.domain;

import dev.uberlan.siscacs.domain.dto.TreinamentoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

interface TreinamentoRepository extends JpaRepository<Treinamento, UUID> {

    @Query("""
            SELECT
             new dev.uberlan.siscacs.domain.dto.TreinamentoDTO(a.id, a.dataTreinamento, new dev.uberlan.siscacs.domain.dto.ArmaDTO(a.arma.id, a.arma.calibre, a.arma.descricao), a.quantidadeTiros, a.pontuacao, a.observacao)
            FROM Treinamento a
            WHERE a.id = ?1
            """)
    Optional<TreinamentoDTO> findTreinamentoById(UUID id);

    @Query("""
            SELECT
             new dev.uberlan.siscacs.domain.dto.TreinamentoDTO(a.id, a.dataTreinamento, new dev.uberlan.siscacs.domain.dto.ArmaDTO(a.arma.id, a.arma.calibre, a.arma.descricao), a.quantidadeTiros, a.pontuacao, a.observacao)
            FROM Treinamento a
            WHERE a.arma = ?1
            ORDER BY a.dataTreinamento DESC
            """)
    List<TreinamentoDTO> findTreinamentosByArma(Arma arma);

    @Query("""
            SELECT
             new dev.uberlan.siscacs.domain.dto.TreinamentoDTO(a.id, a.dataTreinamento, new dev.uberlan.siscacs.domain.dto.ArmaDTO(a.arma.id, a.arma.calibre, a.arma.descricao), a.quantidadeTiros, a.pontuacao, a.observacao)
            FROM Treinamento a
            WHERE EXISTS (
                           SELECT 1
                           FROM Arma b
                           WHERE b.id = a.arma.id
                             AND b.usuario = ?1
                          )
            ORDER BY a.dataTreinamento DESC
            """)
    List<TreinamentoDTO> findTreinamentosByUsuario(Usuario usuario);


//    @Query("""
//            UPDATE Treinamento t
//              SET t.dataTreinamento = :dataTreinamento,
//                  t.quantidadeTiros = :quantidadeTiros,
//                  t.pontuacao = :pontuacao,
//                  t.observacao = :observacao,
//                  t.updatedAt = :updatedAt
//             WHERE t.id = :id
//            """)
    @Modifying
    @Query("UPDATE Treinamento t SET t.dataTreinamento = :dataTreinamento, t.quantidadeTiros = :quantidadeTiros, t.pontuacao = :pontuacao, t.observacao = :observacao, t.updatedAt = :updatedAt WHERE t.id = :id")
    void updateTreinamento(@Param(value = "id") UUID id,
                           @Param(value = "dataTreinamento") LocalDate dataTreinamento,
                           @Param(value = "quantidadeTiros") int quantidadeTiros,
                           @Param(value = "pontuacao") int pontuacao,
                           @Param(value = "observacao") String observacao,
                           @Param(value = "updatedAt") LocalDateTime updatedAt
    );
}
