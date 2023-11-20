package dev.uberlan.siscacs.domain;

import dev.uberlan.siscacs.domain.command.MunicaoUpdateCommand;
import dev.uberlan.siscacs.domain.command.TreinamentoCreateCommand;
import dev.uberlan.siscacs.domain.command.TreinamentoUpdateCommand;
import dev.uberlan.siscacs.domain.dto.ArmaDTO;
import dev.uberlan.siscacs.domain.dto.MunicaoDTO;
import dev.uberlan.siscacs.domain.dto.TreinamentoDTO;
import dev.uberlan.siscacs.exception.TreinamentoInvalidMunicaoException;
import dev.uberlan.siscacs.exception.TreinamentoNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class TreinamentoService {

    private final ArmaService armaService;
    private final MunicaoService municaoService;
    private final TreinamentoRepository treinamentoRepository;
    private final MunicaoRepository municaoRepository;

    public TreinamentoService(ArmaService armaService, MunicaoService municaoService, TreinamentoRepository treinamentoRepository, MunicaoRepository municaoRepository) {
        this.armaService = armaService;
        this.municaoService = municaoService;
        this.treinamentoRepository = treinamentoRepository;
        this.municaoRepository = municaoRepository;
    }

    public Optional<TreinamentoDTO> findTreinamentoById(UUID id) {
        return treinamentoRepository.findTreinamentoById(id);
    }

    public List<TreinamentoDTO> findTreinamentosByArmaId(UUID id) {
        return treinamentoRepository.findTreinamentosByArma(Arma.builder().id(id).build());
    }

    public List<TreinamentoDTO> findTreinamentosByUsuarioId(UUID id) {
        return treinamentoRepository.findTreinamentosByUsuario(Usuario.builder().id(id).build());
    }

    @Transactional
    public TreinamentoDTO createTreinamento(TreinamentoCreateCommand cmd) {
        MunicaoDTO municaoDTO = municaoService.findMunicaoByArmaId(cmd.arma().id()).orElseThrow(() -> TreinamentoInvalidMunicaoException.of(String.format("Munição de %s não disponível para o treinamento.", cmd.arma().calibre().toString())));
        if (municaoDTO.quantidade() < cmd.quantidadeTiros())
            throw TreinamentoInvalidMunicaoException.of(String.format("Munição de %s com quantidade insuficiente para o treinamento.", cmd.arma().calibre().toString()));

        Treinamento treinamento = Treinamento.builder()
                .dataTreinamento(cmd.dataTreinamento())
                .arma(Arma.builder().id(cmd.arma().id()).build())
                .quantidadeTiros(cmd.quantidadeTiros())
                .pontuacao(cmd.pontuacao())
                .observacao(cmd.observacao())
                .createdAt(LocalDateTime.now())
                .build();

        Treinamento saved = treinamentoRepository.save(treinamento);

        MunicaoUpdateCommand municaoUpdateCommand = new MunicaoUpdateCommand(municaoDTO.id(), municaoDTO.arma(), municaoDTO.quantidade() - cmd.quantidadeTiros());
        municaoService.updateMunicao(municaoUpdateCommand);

        return new TreinamentoDTO(saved.getId(), saved.getDataTreinamento(), new ArmaDTO(saved.getArma().getId(), saved.getArma().getCalibre(), saved.getArma().getDescricao()), saved.getQuantidadeTiros(), saved.getPontuacao(), saved.getObservacao());
    }

    @Transactional
    public void updateTreinamento(TreinamentoUpdateCommand cmd) {
//        MunicaoDTO municaoDTO = municaoService.findMunicaoByArmaId(cmd.arma().id()).orElseThrow(() -> TreinamentoInvalidMunicaoException.of(String.format("Munição de %s não disponível para o treinamento.", cmd.arma().calibre().toString())));
//        Municao municao = municaoRepository.findByArma(Arma.builder().id(cmd.arma().id()).build());
        List<Municao> municoes = municaoRepository.findAll();
        Municao municao = municoes.stream().filter(m -> m.getArma().getId().equals(cmd.arma().id())).findFirst().orElseThrow(() -> TreinamentoInvalidMunicaoException.of(String.format("Munição de %s não disponível para o treinamento.", cmd.arma().id().toString())));
        if (municao.getQuantidade() < cmd.quantidadeTiros())
            throw TreinamentoInvalidMunicaoException.of(String.format("Munição de %s com quantidade insuficiente para o treinamento.", cmd.arma().calibre().toString()));
//
////        ArmaDTO armaDTO = armaService.findArmaById(municaoDTO.arma().id()).orElseThrow(() -> ArmaNotFoundException.of(municaoDTO.arma().id()));
//
//        TreinamentoDTO treinamentoDTO = treinamentoRepository.findTreinamentoById(cmd.id()).orElseThrow(() -> TreinamentoNotFoundException.of(cmd.id()));

        Treinamento treinamento = treinamentoRepository.findById(cmd.id()).orElseThrow(() -> TreinamentoNotFoundException.of(cmd.id()));
        boolean isQuantidadeTirosDiferente = treinamento.getQuantidadeTiros() != cmd.quantidadeTiros();

        treinamento.setDataTreinamento(cmd.dataTreinamento());
        treinamento.setQuantidadeTiros(cmd.quantidadeTiros());
        treinamento.setPontuacao(cmd.pontuacao());
        treinamento.setObservacao(cmd.observacao());
        treinamento.setUpdatedAt(LocalDateTime.now());

        treinamentoRepository.save(treinamento);
//
//        treinamentoRepository.updateTreinamento(
//                cmd.id(),
//                cmd.dataTreinamento(),
//                cmd.quantidadeTiros(),
//                cmd.pontuacao(),
//                cmd.observacao(),
//                LocalDateTime.now()
//                );

//        if (treinamentoDTO.quantidadeTiros() != cmd.quantidadeTiros()) {
//            int diferencaTiros = treinamentoDTO.quantidadeTiros() - cmd.quantidadeTiros();
//            MunicaoUpdateCommand municaoUpdateCommand = new MunicaoUpdateCommand(municaoDTO.id(), municaoDTO.arma(), municaoDTO.quantidade() + diferencaTiros);
//            municaoService.updateMunicao(municaoUpdateCommand);
//        }
    }

}
