package dev.uberlan.siscacs.domain;

import dev.uberlan.siscacs.domain.command.UsuarioCreateCommand;
import dev.uberlan.siscacs.domain.command.UsuarioUpdateCommand;
import dev.uberlan.siscacs.domain.dto.UsuarioDTO;
import dev.uberlan.siscacs.exception.UsuarioNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<UsuarioDTO> findUsuarioById(UUID id) {
        return usuarioRepository.findUsuarioById(id);
    }

    public List<UsuarioDTO> findAllUsuarios() {
        return usuarioRepository.findAllUsuarios();
    }

    @Transactional
    public UsuarioDTO createUsuario(UsuarioCreateCommand cmd) {
        Usuario usuario = Usuario.builder()
                .login(cmd.login())
                .nome(cmd.nome())
                .password(cmd.password())
                .cacId(cmd.cacId())
                .createdAt(LocalDateTime.now())
                .build();

        Usuario saved = usuarioRepository.save(usuario);
        return new UsuarioDTO(saved.getId(), saved.getLogin(), saved.getNome(), saved.getCacId());
    }

    @Transactional
    public void updateUsuario(UsuarioUpdateCommand cmd) {

        Usuario usuario = usuarioRepository.findById(cmd.id())
                .orElseThrow(() -> UsuarioNotFoundException.of(cmd.id()));

        usuario.setNome(cmd.nome());
        usuario.setPassword(cmd.password());
        usuario.setCacId(cmd.cacId());
        usuario.setUpdatedAt(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }
}
