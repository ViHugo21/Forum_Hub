package com.Alura.Challenge.Forum_Hub.Model.Topico;

import java.time.LocalDateTime;

import com.Alura.Challenge.Forum_Hub.Model.Curso;

public record DadosRetorno(Long id, String titulo, Curso curso, String mensagem, String nome, LocalDateTime horario) {
    public DadosRetorno(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getCurso(), topico.getMensagem(), topico.getUsuario().getNome(), topico.getHorario());
    }
}
