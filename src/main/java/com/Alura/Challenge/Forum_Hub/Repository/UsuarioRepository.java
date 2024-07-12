package com.Alura.Challenge.Forum_Hub.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Alura.Challenge.Forum_Hub.Model.Usuario.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.login = :login")
    Optional<Usuario> findByLogin(String login);
    
}
