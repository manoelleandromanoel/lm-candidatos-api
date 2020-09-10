package com.example.lm.candidatos.api.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.lm.candidatos.api.event.RecursoCriadoEvent;
import com.example.lm.candidatos.api.exceptionhandler.CandidatosExceptionHandler.Erro;
import com.example.lm.candidatos.api.model.ContaCorrente;
import com.example.lm.candidatos.api.repository.ContaCorrenteRepository;
import com.example.lm.candidatos.api.service.ContaCorrenteService;
import com.example.lm.candidatos.api.service.exception.CandidatoInexistenteException;

@RestController
@RequestMapping("/contacorrente")
@Api(value = "Conta Corrente", tags = {"conta corrente"})
public class ContaCorrenteController {

	@Autowired
	private ContaCorrenteRepository contaCorrenteRepository;
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private ContaCorrenteService contaCorrenteService;
	
	@Autowired
	private MessageSource messageSource;

	/**
	 * - Lista todas as contas correntes
	 * @return
	 */
	@GetMapping(produces = "application/json")
	public List<ContaCorrente> findAll() {
		return contaCorrenteRepository.findAll();
	}
	
	/**
	 * - Busca uma conta corrente pelo id
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Optional<ContaCorrente>> findById(@PathVariable Long id) {
		Optional<ContaCorrente> contaCorrente = contaCorrenteRepository.findById(id);
		
		return contaCorrente.isPresent() ? ResponseEntity.ok(contaCorrente) : ResponseEntity.notFound().build();
	}
	
	/**
	 * - Adiciona uma conta corrente
	 * @param contaCorrente
	 * @param response
	 * @return
	 */
	@PostMapping
	public ResponseEntity<ContaCorrente> addContaCorrente(@Valid @RequestBody ContaCorrente contaCorrente, HttpServletResponse response) {
		ContaCorrente contaCorrenteSalva = contaCorrenteService.salvar(contaCorrente); 
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, contaCorrenteSalva.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(contaCorrenteSalva);
	}

	/**
	 * - Atualiza uma conta corrente
	 * @param id
	 * @param contaCorrente
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<ContaCorrente> updateContaCorrente(@PathVariable Long id, @Valid @RequestBody ContaCorrente contaCorrente){
		try {
			ContaCorrente contaCorrenteSalva = contaCorrenteService.atualizar(id, contaCorrente);
			
			return ResponseEntity.ok(contaCorrenteSalva);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	/**
	 * - Deleta uma conta corrente
	 * @param id
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteContaCorrente(@PathVariable Long id) {
		contaCorrenteRepository.deleteById(id);
	}
	
	@ExceptionHandler({ CandidatoInexistenteException.class })
	public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(CandidatoInexistenteException ex){
		String mensagemUsuario = messageSource.getMessage("candidato.inexistente", null, LocaleContextHolder.getLocale());
		String mensagemDesenvolvedor = ex.toString();
		List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor));
		
		return ResponseEntity.badRequest().body(erros);
	}
}
