package com.forumhub.forumhub.dto;

import com.forumhub.forumhub.model.Topico.StatusTopico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicoDTO(
        @NotBlank String titulo,
        @NotBlank String mensagem,
        @NotNull Long cursoId,
        @NotNull StatusTopico status
) {}