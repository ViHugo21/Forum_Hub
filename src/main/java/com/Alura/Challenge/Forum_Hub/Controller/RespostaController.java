package com.Alura.Challenge.Forum_Hub.Controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Alura.Challenge.Forum_Hub.Model.Resposta.DadosCadastroResposta;
import com.Alura.Challenge.Forum_Hub.Model.Resposta.DadosAtualizadoResposta;
import com.Alura.Challenge.Forum_Hub.Model.Resposta.Resposta;
import com.Alura.Challenge.Forum_Hub.Model.Resposta.RetornoResposta;
import com.Alura.Challenge.Forum_Hub.Model.Usuario.Usuario;
import com.Alura.Challenge.Forum_Hub.Repository.RespostaRepository;
import com.Alura.Challenge.Forum_Hub.Repository.TopicoRepository;
import com.Alura.Challenge.Forum_Hub.Repository.UsuarioRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/resposta")
@SecurityRequirement(name = "bearer-key")
public class RespostaController {
    
    @Autowired
    private RespostaRepository respostaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity responderTopico(@RequestBody @Valid DadosCadastroResposta dados, @PathVariable Long id, HttpServletRequest request) {

        var topico = topicoRepository.findById(id);

        if (topico.isPresent() && topico.get().getAtivo()) {
            var respostasTopico = topico.get().getRespostas();

            var user = recuperarUsuario(request);
                var respostasUsuario = user.getRespostas();

            var resposta = new Resposta();
                resposta.setHorario(LocalDateTime.now());
                resposta.setMensagem(dados.mensagem());
                resposta.setUsuario(user);
                resposta.setTopico(topico.get());

            respostasTopico.add(resposta);
            respostasUsuario.add(resposta);

            respostaRepository.save(resposta);

            return ResponseEntity.ok().body(new RetornoResposta(resposta));
        } else {
            return ResponseEntity.badRequest().body("Não é possível responder um tópico encerrado.");
        }
        
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizarResposta(@RequestBody DadosAtualizadoResposta dados, @PathVariable Long id, HttpServletRequest request) {

        var resposta = respostaRepository.findById(id);
        
        var user = recuperarUsuario(request);

        if (resposta.isPresent()) {

            if (resposta.get().getUsuario().getLogin().equals(user.getLogin())) {
                resposta.get().atualizarResposta(dados);

                respostaRepository.save(resposta.get());

                return ResponseEntity.ok().body(new RetornoResposta(resposta.get()));
            } else {
                return ResponseEntity.badRequest().body("Não é possível atualizar uma resposta de outro usuário");
            }
        } else {
            return ResponseEntity.badRequest().body("Resposta não encontrada");
        }
    } 



    public Usuario recuperarUsuario(HttpServletRequest request) {

        var initIndex = request.getUserPrincipal().getName().indexOf("login=");

        var lastIndex = request.getUserPrincipal().getName().indexOf(",", initIndex);

        var userName = request.getUserPrincipal().getName().substring(initIndex, lastIndex).replace("login=", "");

        var usuario = usuarioRepository.findByLogin(userName);

        return usuario.get();
    }
}
