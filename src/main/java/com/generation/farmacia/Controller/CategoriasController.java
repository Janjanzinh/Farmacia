package com.generation.farmacia.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.farmacia.Model.Categoria;
import com.generation.farmacia.Repository.CategoriaRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoriasController {

	@Autowired
	private CategoriaRepository categoriasRepository;

	@GetMapping
	public ResponseEntity<List<Categoria>> getAll() {
		return ResponseEntity.ok(categoriasRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Categoria> getById(@PathVariable Long id) {
		return categoriasRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@GetMapping("/descricao/{descricao}")
	public ResponseEntity<List<Categoria>> getByDescricao(@PathVariable String descricao) {
		return ResponseEntity.ok(categoriasRepository.findAllByDescricaoContainingIgnoreCase(descricao));
	}

	@PutMapping
	public ResponseEntity<Categoria> put(@Valid @RequestBody Categoria categorias) {
		return categoriasRepository.findById(categorias.getId())
				.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(categoriasRepository.save(categorias)))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		Optional<Categoria> categorias = categoriasRepository.findById(id);

		if (categorias.isEmpty())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);

		categoriasRepository.deleteById(id);
	}
}