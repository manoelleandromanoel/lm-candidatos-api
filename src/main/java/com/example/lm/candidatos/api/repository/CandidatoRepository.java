package com.example.lm.candidatos.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.lm.candidatos.api.model.Candidato;

public interface CandidatoRepository extends JpaRepository<Candidato, Long> {

}
