package com.Alura.Challenge.Forum_Hub.Model.Topico;

import java.time.LocalDateTime;

import com.Alura.Challenge.Forum_Hub.Model.Curso;

public record ListagemTopicos(Long id, String titulo, Curso curso, String mensagem, Integer respostas, String usuario_nome,
            LocalDateTime horario, Boolean ativo) {
    
    public ListagemTopicos(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getCurso(), topico.getMensagem(), (topico.getRespostas().size()), (topico.getUsuario().getNome()), topico.getHorario(), topico.getAtivo());
    }
    
}
