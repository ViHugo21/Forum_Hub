package com.Alura.Challenge.Forum_Hub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Alura.Challenge.Forum_Hub.Model.Resposta.Resposta;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {
    
}
