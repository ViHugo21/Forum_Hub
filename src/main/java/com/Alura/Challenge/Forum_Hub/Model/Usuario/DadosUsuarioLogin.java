package com.Alura.Challenge.Forum_Hub.Model.Usuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosUsuarioLogin(@NotBlank String login,
                                @NotBlank String senha) {
    
}
