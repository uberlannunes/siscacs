package dev.uberlan.siscacs.domain;

import dev.uberlan.siscacs.domain.command.ArmaCreateCommand;
import dev.uberlan.siscacs.domain.command.ArmaUpdateCommand;
import dev.uberlan.siscacs.domain.dto.ArmaDTO;
import dev.uberlan.siscacs.exception.ArmaNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ArmaService {

    private final ArmaRepository armaRepository;

    public ArmaService(ArmaRepository ArmaRepository) {
        this.armaRepository = ArmaRepository;
    }

    public Optional<ArmaDTO> findArmaById(UUID id) {
        return armaRepository.findArmaById(id);
    }

    public List<ArmaDTO> findArmasByUsuario(UUID id) {
        return armaRepository.findArmaByUsuarioId(Usuario.builder().id(id).build());
    }

    @Transactional
    public ArmaDTO createArma(ArmaCreateCommand cmd) {
        Arma arma = Arma.builder()
                .calibre(cmd.calibre())
                .descricao(cmd.descricao())
                .dataCompra(cmd.dataCompra())
                .observacao(cmd.observacao())
                .usuario(Usuario.builder().id(cmd.usuarioId()).build())
                .createdAt(LocalDateTime.now())
                .build();

        Arma saved = armaRepository.save(arma);
        return new ArmaDTO(saved.getId(), saved.getCalibre(), saved.getDescricao(), saved.getDataCompra(), saved.getDataVenda(), saved.getUsuario().getId(), saved.getObservacao());
    }

    @Transactional
    public void updateArma(ArmaUpdateCommand cmd) {

        Arma arma = armaRepository.findById(cmd.id())
                .orElseThrow(() -> ArmaNotFoundException.of(cmd.id()));

        arma.setCalibre(cmd.calibre());
        arma.setDescricao(cmd.descricao());
        arma.setDataCompra(cmd.dataCompra());
        arma.setObservacao(cmd.observacao());
        arma.setUpdatedAt(LocalDateTime.now());
        armaRepository.save(arma);
    }
}
