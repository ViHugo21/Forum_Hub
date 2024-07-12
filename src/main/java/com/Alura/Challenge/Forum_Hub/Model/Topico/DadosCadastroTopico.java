package com.Alura.Challenge.Forum_Hub.Model.Topico;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosCadastroTopico(@NotBlank(message = "{titulo.obrigatorio}") String titulo, 
                                  @NotBlank(message = "{curso.obrigatorio}") String curso, 
                                  @NotBlank(message = "{mensagem.obrigatorio}") String mensagem) {
    
}
