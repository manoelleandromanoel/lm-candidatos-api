package com.example.lm.candidatos.api.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.lm.candidatos.api.model.Candidato;
import com.example.lm.candidatos.api.model.ContaCorrente;
import com.example.lm.candidatos.api.repository.CandidatoRepository;
import com.example.lm.candidatos.api.repository.ContaCorrenteRepository;
import com.example.lm.candidatos.api.service.exception.CandidatoInexistenteException;
import com.example.lm.candidatos.api.service.exception.ContaCorrenteInexistenteException;

@Service
public class ContaCorrenteService {

	@Autowired
	private CandidatoRepository candidatoRepository;
	
	@Autowired
	private ContaCorrenteRepository contaCorrenteRepository;
	
	public ContaCorrente salvar(ContaCorrente contaCorrente) {
		Optional<Candidato> candidato = buscarCandidatoExistente(contaCorrente.getCandidato().getId());
		
		if (!candidato.isPresent()) {
			throw new CandidatoInexistenteException();
		}
		
		return contaCorrenteRepository.save(contaCorrente);
	}

	public ContaCorrente atualizar(Long id, @Valid ContaCorrente contaCorrente) {
		Optional<ContaCorrente> contaCorrenteSalva = buscarContaCorrenteExistente(id);
		
		if (!contaCorrenteSalva.isPresent()) {
			throw new ContaCorrenteInexistenteException();
		}
		
		contaCorrenteSalva.get().setBanco(contaCorrente.getBanco());
		contaCorrenteSalva.get().setAgencia(contaCorrente.getAgencia());
		contaCorrenteSalva.get().setNumeroConta(contaCorrente.getNumeroConta());
		
		return contaCorrenteRepository.save(contaCorrenteSalva.get());
	}
	
	private Optional<Candidato> buscarCandidatoExistente(Long id) {
		Optional<Candidato> candidato = candidatoRepository.findById(id);
		
		if (candidato == null) {
			throw new IllegalArgumentException();
		}
		
		return candidato;
	}
	
	private Optional<ContaCorrente> buscarContaCorrenteExistente(Long id) {
		Optional<ContaCorrente> contaCorrente = contaCorrenteRepository.findById(id);
		
		if (!contaCorrente.isPresent()) {
			throw new IllegalArgumentException();
		}
		
		return contaCorrente;
	}
}
