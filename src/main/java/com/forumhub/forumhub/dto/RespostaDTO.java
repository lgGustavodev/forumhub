package com.forumhub.forumhub.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RespostaDTO(
        @NotBlank String mensagem,
        @NotNull Long topicoId
) {}