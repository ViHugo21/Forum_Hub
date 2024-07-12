package com.Alura.Challenge.Forum_Hub.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Alura.Challenge.Forum_Hub.Model.Usuario.DadosCadastroUsuario;
import com.Alura.Challenge.Forum_Hub.Model.Usuario.DadosUsuarioLogin;
import com.Alura.Challenge.Forum_Hub.Model.Usuario.Usuario;
import com.Alura.Challenge.Forum_Hub.Repository.UsuarioRepository;
import com.Alura.Challenge.Forum_Hub.Service.DadosTokenJWT;
import com.Alura.Challenge.Forum_Hub.Service.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class UsuarioController {
    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DadosTokenJWT> efetuarLogin(@RequestBody @Valid DadosUsuarioLogin DadosUsuario) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(DadosUsuario.login(), DadosUsuario.senha());

        var authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }

    @PostMapping("/cadastro")
    @Transactional
    public ResponseEntity<DadosCadastroUsuario> cadastrarUsuario(@RequestBody @Valid DadosCadastroUsuario dados) {
        String senha = new BCryptPasswordEncoder().encode(dados.senha());
        Usuario usuario = new Usuario();
        usuario.setLogin(dados.login());
        usuario.setSenha(senha);
        usuario.setNome(dados.nome());
        usuario.setAtivo(true);
        
        repository.save(usuario);

        return ResponseEntity.ok().build();
    }
}
