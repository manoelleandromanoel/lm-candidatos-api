package com.example.lm.candidatos.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lm.candidatos.api.model.Candidato;
import com.example.lm.candidatos.api.repository.CandidatoRepository;
import com.example.lm.candidatos.api.service.exception.CandidatoInexistenteException;

@Service
public class CandidatoService {

	@Autowired
	private CandidatoRepository candidatoRepository;
	
	public Candidato atualizar(Long id, Candidato candidato) {
		Candidato candidatoSalvo = buscarCandidatoExistente(id);
		
		if (candidatoSalvo == null) {
			throw new CandidatoInexistenteException();
		}
		
		BeanUtils.copyProperties(candidato, candidatoSalvo, "id");
		
		return candidatoRepository.save(candidatoSalvo);
	}
	
	private Candidato buscarCandidatoExistente(Long id) {
		Candidato candidatoSalvo = candidatoRepository.getOne(id);
		
		if (candidatoSalvo == null) {
			throw new IllegalArgumentException();
		}
		
		return candidatoSalvo;
	}
}
