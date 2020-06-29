package com.example.lm.candidatos.api.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.lm.candidatos.api.event.RecursoCriadoEvent;
import com.example.lm.candidatos.api.model.Candidato;
import com.example.lm.candidatos.api.repository.CandidatoRepository;
import com.example.lm.candidatos.api.service.CandidatoService;

@RestController
@RequestMapping("/candidatos")
public class CandidatoController {

	@Autowired
	private CandidatoRepository candidatoRepository;
	
	@Autowired
	private CandidatoService candidatoService;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	/**
	 * - Lista todos os candidatos
	 * @return
	 */
	@GetMapping
	public List<Candidato> findAll() {
		return candidatoRepository.findAll();
	}
	
	/**
	 * - Busca um candidato pelo id
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Optional<Candidato>> findById(@PathVariable Long id) {
		Optional<Candidato> candidato = candidatoRepository.findById(id);
		
		return candidato.isPresent() ? ResponseEntity.ok(candidato) : ResponseEntity.notFound().build();
	}
	
	/**
	 * - Adiciona um candidato
	 * @param candidato
	 * @param response
	 * @return
	 */
	@PostMapping
	public ResponseEntity<Candidato> addCandidato(@Valid @RequestBody Candidato candidato, HttpServletResponse response) {
		Candidato candidatoSalvo = candidatoRepository.save(candidato);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, candidatoSalvo.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(candidatoSalvo);
	}
	
	/**
	 * - Atualiza um candidato
	 * @param id
	 * @param candidato
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Candidato> updateCandidato(@PathVariable Long id, @Valid @RequestBody Candidato candidato){
		try {
			Candidato candidatoSalvo = candidatoService.atualizar(id, candidato);
			
			return ResponseEntity.ok(candidatoSalvo);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
	 * - Deleta um candidato
	 * @param id
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCandidato(@PathVariable Long id) {
		candidatoRepository.deleteById(id);
	}
}
