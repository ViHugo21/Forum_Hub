package com.Alura.Challenge.Forum_Hub.Model.Resposta;

import java.time.LocalDateTime;

import com.Alura.Challenge.Forum_Hub.Model.Topico.Topico;
import com.Alura.Challenge.Forum_Hub.Model.Usuario.Usuario;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "respostas")
public class Resposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensagem;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    private Topico topico;

    private LocalDateTime horario;

    public Resposta() {
    }

    public Resposta(Long id, String mensagem, Usuario usuario, Topico topico, LocalDateTime horario) {
        this.id = id;
        this.mensagem = mensagem;
        this.usuario = usuario;
        this.topico = topico;
        this.horario = horario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Topico getTopico() {
        return topico;
    }

    public void setTopico(Topico topico) {
        this.topico = topico;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    @Override
    public String toString() {
        return "Resposta [id=" + id + ", mensagem=" + mensagem + ", usuario=" + usuario.getNome() + ", topico=" + topico.getTitulo()
                + ", horario=" + horario + "]";
    }

    public void atualizarResposta(DadosAtualizadoResposta dados) {
        if (dados.mensagem() != null) {
            setMensagem(dados.mensagem());
        }
    }

    
}
