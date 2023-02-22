package br.edu.ifms.EasyComp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifms.EasyComp.modelo.Jogos;

public interface JogosRepository extends JpaRepository<Jogos, Long>{
	Jogos findByNome(String nome);
}
