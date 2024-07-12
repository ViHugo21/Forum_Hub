package com.Alura.Challenge.Forum_Hub.Model.Usuario;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosCadastroUsuario(@NotBlank(message = "nome.obrigatorio") String nome, 
                                   @NotBlank(message = "login.obrigatorio") String login, 
                                   @NotBlank(message = "senha.obrigatorio") String senha) {
    
}
