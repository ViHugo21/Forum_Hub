package com.Alura.Challenge.Forum_Hub.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Alura.Challenge.Forum_Hub.Model.Topico.DadosCadastroTopico;
import com.Alura.Challenge.Forum_Hub.Model.Topico.DadosRetorno;
import com.Alura.Challenge.Forum_Hub.Model.Topico.DadosTopicoAtualizado;
import com.Alura.Challenge.Forum_Hub.Model.Topico.DetalharTopico;
import com.Alura.Challenge.Forum_Hub.Model.Topico.ListagemTopicos;
import com.Alura.Challenge.Forum_Hub.Model.Topico.ListagemTopicosFiltrados;
import com.Alura.Challenge.Forum_Hub.Model.Topico.Topico;
import com.Alura.Challenge.Forum_Hub.Model.Usuario.Usuario;
import com.Alura.Challenge.Forum_Hub.Repository.TopicoRepository;
import com.Alura.Challenge.Forum_Hub.Repository.UsuarioRepository;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/topico")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    public ResponseEntity<DadosRetorno> cadastrarTopico(@RequestBody @Valid DadosCadastroTopico dados, HttpServletRequest request) {

        var usuario = recuperarUsuario(request);

        var topico = new Topico(dados);
    
        var topicosUsuario = usuario.getTopicos();

        topico.setUsuario(usuario);

        topicosUsuario.add(topico);

        usuario.setTopicos(topicosUsuario);

        topicoRepository.save(topico);

        return ResponseEntity.ok().body(new DadosRetorno(topico));
    }

    @GetMapping
    public ResponseEntity<Page<ListagemTopicos>> listarTodosTopicos(@PageableDefault(sort = "horario",size = 10) Pageable paginacao) {
        var topicos = topicoRepository.findAll(paginacao).map(ListagemTopicos::new);

        return ResponseEntity.ok().body(topicos);
    }

    @GetMapping("/ativo")
    public ResponseEntity<Page<ListagemTopicosFiltrados>> listarTopicosAtivos(@PageableDefault(sort = "horario", size = 10) Pageable paginacao) {
        var topicos = topicoRepository.findAllByAtivo(paginacao).map(ListagemTopicosFiltrados::new);

        return ResponseEntity.ok().body(topicos);
    }

    @GetMapping("/encerrado")
    public ResponseEntity<Page<ListagemTopicosFiltrados>> listarTopicosEncerrados(@PageableDefault(sort = "horario", size = 10) Pageable paginacao) {
        var topicos = topicoRepository.findAllByDesativado(paginacao).map(ListagemTopicosFiltrados::new);

        return ResponseEntity.ok().body(topicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity detalharTopico(@PathVariable Long id) {
        var topico = topicoRepository.findById(id);

        if (topico.isPresent()) {
            var topicoDetalhado = new DetalharTopico(topico.get());

            return ResponseEntity.ok().body(topicoDetalhado);
        } else {
            return ResponseEntity.badRequest().body("Tópico não encontrado");
        }
        
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity atualizarTopico(@RequestBody DadosTopicoAtualizado dados, @PathVariable Long id, HttpServletRequest request) {

        var userName = recuperarUsuario(request); 

        var topicoAntigo = topicoRepository.findById(id);

        if (topicoAntigo.isPresent()) {

            if (topicoAntigo.get().getUsuario().getLogin().equals(userName.getLogin())) {

                if (topicoAntigo.get().getAtivo()) {
                    topicoAntigo.get().atualizarTopico(dados);

                    topicoRepository.save(topicoAntigo.get());

                    return ResponseEntity.ok().body(new DadosRetorno(topicoAntigo.get()));
                }

                return ResponseEntity.badRequest().body("Não é possível atualizar um tópico desativado");
                
            } else {
                return ResponseEntity.badRequest().body("Não é possível alterar um tópico criado por outro usuário");
            }
            
        }

        return ResponseEntity.badRequest().body("Tópico não encontrado");
        
    }

    @DeleteMapping("/{id}")
    public ResponseEntity encerrarTopico(@PathVariable Long id, HttpServletRequest request) {
        var userName = recuperarUsuario(request);

        var topico = topicoRepository.findById(id);

        if (topico.isPresent()) {

            if (topico.get().getUsuario().getLogin().equals(userName.getLogin())) {

                topico.get().setAtivo(false);

                topicoRepository.save(topico.get());

                return ResponseEntity.ok().body(new DadosRetorno(topico.get()));
            } else {
                return ResponseEntity.badRequest().body("Não é possível encerrar um tópico criado por outro usuário");
            }
            
        }

        return ResponseEntity.badRequest().body("Tópico não encontrado");
    }

    public Usuario recuperarUsuario(HttpServletRequest request) {

        var initIndex = request.getUserPrincipal().getName().indexOf("login=");

        var lastIndex = request.getUserPrincipal().getName().indexOf(",", initIndex);

        var userName = request.getUserPrincipal().getName().substring(initIndex, lastIndex).replace("login=", "");

        var usuario = usuarioRepository.findByLogin(userName);

        return usuario.get();
    }
}
