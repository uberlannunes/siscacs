package dev.uberlan.siscacs.domain;

import dev.uberlan.siscacs.domain.command.UsuarioCreateCommand;
import dev.uberlan.siscacs.domain.command.UsuarioUpdateCommand;
import dev.uberlan.siscacs.domain.command.UsuarioUpdateDadosPessoaisCommand;
import dev.uberlan.siscacs.domain.command.UsuarioUpdatePasswordCommand;
import dev.uberlan.siscacs.domain.dto.UsuarioDTO;
import dev.uberlan.siscacs.exception.UsuarioNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
    }

    public Optional<Usuario> findUsuarioByLogin(String login) {
        return Optional.ofNullable(usuarioRepository.findByLogin(login));
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
                .password(passwordEncoder.encode(cmd.password()))
                .cacId(cmd.cacId())
                .createdAt(LocalDateTime.now())
                .build();

        Usuario saved = usuarioRepository.save(usuario);
        return new UsuarioDTO(saved.getId(), saved.getLogin(), saved.getNome(), saved.getCacId());
    }

    @Transactional
    public void updateUsuario(UsuarioUpdateCommand cmd) {

        Usuario usuario = usuarioRepository.findById(cmd.id())
                .orElseThrow(() -> UsuarioNotFoundException.of(cmd.id().toString()));

        usuario.setNome(cmd.nome());
        usuario.setPassword(passwordEncoder.encode(cmd.password()));
        usuario.setCacId(cmd.cacId());
        usuario.setUpdatedAt(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void updateDadosPessoais(UsuarioUpdateDadosPessoaisCommand cmd) {

        Usuario usuario = usuarioRepository.findById(cmd.id())
                .orElseThrow(() -> UsuarioNotFoundException.of(cmd.id().toString()));

        usuario.setNome(cmd.nome());
        usuario.setCacId(cmd.cacId());
        usuario.setUpdatedAt(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }

    @Transactional
    public void updatePassword(UsuarioUpdatePasswordCommand cmd) {

        Usuario usuario = usuarioRepository.findById(cmd.id())
                .orElseThrow(() -> UsuarioNotFoundException.of(cmd.id().toString()));

        usuario.setPassword(passwordEncoder.encode(cmd.password()));
        usuario.setUpdatedAt(LocalDateTime.now());
        usuarioRepository.save(usuario);
    }
}
