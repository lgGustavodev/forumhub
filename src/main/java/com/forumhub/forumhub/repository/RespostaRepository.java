package com.forumhub.forumhub.repository;

import com.forumhub.forumhub.model.Resposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RespostaRepository extends JpaRepository<Resposta, Long> {

    @Query("""
            SELECT r FROM Resposta r
            WHERE r.topico.id = :idTopico
            AND r.ativo = true
            """)
    List<Resposta> findAllByTopicoIdAndAtivoTrue(Long idTopico);
}