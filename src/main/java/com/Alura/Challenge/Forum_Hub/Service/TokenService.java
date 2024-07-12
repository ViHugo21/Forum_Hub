package com.Alura.Challenge.Forum_Hub.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.Alura.Challenge.Forum_Hub.Model.Usuario.Usuario;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;

@Service
public class TokenService {
    
    private final String senha = "vitto4286";

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(senha);
            String token = JWT.create()
                .withIssuer("API Forum.Hub")
                .withSubject(usuario.getLogin())
                .withExpiresAt(dataExpiracao())
                .sign(algorithm);
            return token;
        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token JWT", e);
        }
    }

    public String getSubject(String token) {
        try {
            var algorithm = Algorithm.HMAC256(senha);
            return JWT.require(algorithm)
                    .withIssuer("API Forum.Hub")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Token inválido ou expirado");
        }
    }

    public void validateToken(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(senha);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("API Forum.Hub")
                    .build();

            verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("Erro ao validar o token: ", e);
        }
    }

    private Instant dataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
