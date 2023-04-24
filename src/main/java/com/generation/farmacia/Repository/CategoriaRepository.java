package com.generation.farmacia.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.generation.farmacia.Model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
	public List <Categoria> findAllByDescricaoContainingIgnoreCase (String descricao);

}