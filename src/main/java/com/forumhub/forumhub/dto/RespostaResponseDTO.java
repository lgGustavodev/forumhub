package com.forumhub.forumhub.dto;

import com.forumhub.forumhub.model.Resposta;
import java.time.LocalDateTime;

public record RespostaResponseDTO(
        Long id,
        String mensagem,
        LocalDateTime dataCriacao,
        String autor,
        boolean solucao,
        boolean ativo
) {
    public RespostaResponseDTO(Resposta resposta) {
        this(
                resposta.getId(),
                resposta.getMensagem(),
                resposta.getDataCriacao(),
                resposta.getAutor().getNome(), // Ou getEmail() conforme sua necessidade
                resposta.isSolucao(),
                resposta.isAtivo()
        );
    }
}