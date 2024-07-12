package com.Alura.Challenge.Forum_Hub.Model.Topico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.Alura.Challenge.Forum_Hub.Model.Curso;
import com.Alura.Challenge.Forum_Hub.Model.Resposta.Resposta;
import com.Alura.Challenge.Forum_Hub.Model.Usuario.Usuario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "topicos")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Enumerated(EnumType.STRING)
    private Curso curso;

    private String mensagem;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Resposta> respostas;

    @ManyToOne(fetch = FetchType.LAZY)
    private Usuario usuario;

    private LocalDateTime horario;

    private Boolean ativo;

    public Topico() {
    }

    public Topico(DadosCadastroTopico dados) {
        setTitulo(dados.titulo());
        setCurso(dados.curso());
        setMensagem(dados.mensagem());
        setHorario(LocalDateTime.now());
        setAtivo(true);
    }

    public Topico(Long id, String titulo, String curso, String mensagem, List<Resposta> respostas, Usuario usuario,
            LocalDateTime horario, Boolean ativo) {
        this.id = id;
        this.titulo = titulo;
        this.curso = Curso.fromString(curso);
        this.mensagem = mensagem;
        this.respostas = respostas;
        this.usuario = usuario;
        this.horario = horario;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = Curso.fromString(curso);
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<Resposta> getRespostas() {
        return respostas;
    }

    public void setRespostas(List<Resposta> respostas) {
        this.respostas = respostas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public LocalDateTime getHorario() {
        return horario;
    }

    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Topico [id=" + id + ", titulo=" + titulo + ", curso=" + curso + ", mensagem=" + mensagem
                + ", respostas=" + respostas.stream().map(r ->r.getId() + r.getMensagem()).collect(Collectors.toList()) + ", usuario=" + usuario.getNome() + ", horario=" + horario + "]";
    }

    public void atualizarTopico(DadosTopicoAtualizado dados) {
        if (dados.titulo() != null) {
            setTitulo(dados.titulo());
        }

        if (dados.curso() != null) {
            this.curso = dados.curso();
        }

        if (dados.mensagem() != null) {
            setMensagem(dados.mensagem());
        }
        setHorario(LocalDateTime.now());
    }

    
}
