package com.Alura.Challenge.Forum_Hub.Model.Resposta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosCadastroResposta(@NotBlank(message = "{mensagem.obrigatorio}") String mensagem) {
    
}
