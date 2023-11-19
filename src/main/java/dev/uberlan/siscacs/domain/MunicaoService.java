package dev.uberlan.siscacs.domain;

import dev.uberlan.siscacs.domain.command.MunicaoCreateCommand;
import dev.uberlan.siscacs.domain.command.MunicaoUpdateCommand;
import dev.uberlan.siscacs.domain.dto.ArmaDTO;
import dev.uberlan.siscacs.domain.dto.MunicaoDTO;
import dev.uberlan.siscacs.exception.MunicaoInvalidDataException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class MunicaoService {

    private final MunicaoRepository municaoRepository;

    public MunicaoService(MunicaoRepository municaoRepository) {
        this.municaoRepository = municaoRepository;
    }

    public Optional<MunicaoDTO> findMunicaoById(UUID id) {
        return municaoRepository.findMunicaoById(id);
    }

    public List<MunicaoDTO> findMunicoesByUsuarioId(UUID id) {
        return municaoRepository.findMunicoesByUsuarioId(Usuario.builder().id(id).build());
    }

    public Optional<MunicaoDTO> findMunicaoByArmaId(UUID armaId) {
        return municaoRepository.findMunicaoByArma(Arma.builder().id(armaId).build());
    }

    @Transactional
    public MunicaoDTO saveMunicao(MunicaoCreateCommand cmd) {

        MunicaoDTO municaoDTO = findMunicaoByArmaId(cmd.arma().id())
                .map(m -> {
                    MunicaoUpdateCommand newCmd = new MunicaoUpdateCommand(m.id(), new ArmaDTO(m.arma().id()), m.quantidade() + cmd.quantidade());
                    updateMunicao(newCmd);
                    return new MunicaoDTO(m.id(), newCmd.arma(), newCmd.quantidade());
                })
                .orElseGet(() -> {
                    Municao municao = Municao.builder()
                            .arma(Arma.builder().id(cmd.arma().id()).build())
                            .quantidade(cmd.quantidade()).build();

                    Municao savedMunicao = municaoRepository.save(municao);
                    ArmaDTO armaDTO = new ArmaDTO(savedMunicao.getArma().getId());
                    return new MunicaoDTO(savedMunicao.getId(), armaDTO, savedMunicao.getQuantidade());
                });

        return municaoDTO;
    }

    @Transactional
    public void updateMunicao(MunicaoUpdateCommand cmd) {
        if (cmd.quantidade() < 0)
            throw MunicaoInvalidDataException.of(cmd.id());

        municaoRepository.updateQuantidade(cmd.id(), cmd.quantidade());
    }
}
