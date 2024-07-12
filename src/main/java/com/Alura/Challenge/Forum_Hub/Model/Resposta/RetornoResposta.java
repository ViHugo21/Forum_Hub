package com.Alura.Challenge.Forum_Hub.Model.Resposta;

import java.time.LocalDateTime;

public record RetornoResposta(String titulo_topico, String mensagem, String nome, LocalDateTime horario) {
    public RetornoResposta(Resposta resposta) {
        this(resposta.getTopico().getTitulo(), resposta.getMensagem(), resposta.getUsuario().getNome(), resposta.getHorario());
    }
}
