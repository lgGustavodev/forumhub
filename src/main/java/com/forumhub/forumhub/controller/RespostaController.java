package com.forumhub.forumhub.controller;

import com.forumhub.forumhub.dto.*;
import com.forumhub.forumhub.model.Resposta;
import com.forumhub.forumhub.model.Topico;
import com.forumhub.forumhub.model.Usuario;
import com.forumhub.forumhub.repository.RespostaRepository;
import com.forumhub.forumhub.repository.TopicoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/respostas")
public class RespostaController {

    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<RespostaResponseDTO> cadastrar(
            @RequestBody @Valid RespostaDTO dto,
            UriComponentsBuilder uriBuilder,
            @AuthenticationPrincipal Usuario usuario
    ) {
        var topico = topicoRepository.findById(dto.topicoId())
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));

        var resposta = new Resposta();
        resposta.setMensagem(dto.mensagem());
        resposta.setTopico(topico);
        resposta.setAutor(usuario);
        resposta.setDataCriacao(LocalDateTime.now());
        resposta.setSolucao(false);
        resposta.setAtivo(true);

        respostaRepository.save(resposta);

        URI uri = uriBuilder.path("/respostas/{id}").buildAndExpand(resposta.getId()).toUri();
        return ResponseEntity.created(uri).body(new RespostaResponseDTO(resposta));
    }

    @GetMapping("/topico/{idTopico}")
    public ResponseEntity<List<RespostaResponseDTO>> listarPorTopico(@PathVariable Long idTopico) {
        var respostas = respostaRepository.findAllByTopicoIdAndAtivoTrue(idTopico)
                .stream()
                .map(RespostaResponseDTO::new)
                .toList();

        return ResponseEntity.ok(respostas);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<RespostaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid RespostaDTO dto,
            @AuthenticationPrincipal Usuario usuario
    ) {
        var resposta = respostaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resposta não encontrada"));

        if (!resposta.getAutor().equals(usuario) && !usuario.getRole().equals(Usuario.Role.ADMIN)) {
            return ResponseEntity.status(403).build();
        }

        var topico = topicoRepository.findById(dto.topicoId())
                .orElseThrow(() -> new EntityNotFoundException("Tópico não encontrado"));

        resposta.setMensagem(dto.mensagem());
        resposta.setTopico(topico);

        return ResponseEntity.ok(new RespostaResponseDTO(resposta));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuario
    ) {
        var resposta = respostaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resposta não encontrada"));

        if (!resposta.getAutor().equals(usuario) && !usuario.getRole().equals(Usuario.Role.ADMIN)) {
            return ResponseEntity.status(403).build();
        }

        resposta.setAtivo(false);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/solucao")
    @Transactional
    public ResponseEntity<Void> marcarComoSolucao(
            @PathVariable Long id,
            @AuthenticationPrincipal Usuario usuario
    ) {
        var resposta = respostaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Resposta não encontrada"));
        var topico = resposta.getTopico();

        if (!topico.getAutor().equals(usuario) && !usuario.getRole().equals(Usuario.Role.ADMIN)) {
            return ResponseEntity.status(403).build();
        }

        resposta.setSolucao(true);
        topico.setStatus(Topico.StatusTopico.SOLUCIONADO);

        return ResponseEntity.ok().build();
    }
}