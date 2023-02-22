package br.edu.ifms.EasyComp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifms.EasyComp.modelo.Torneio;

public interface TorneioRepository extends JpaRepository<Torneio, Long>{
	Torneio findByNome(String nome);
}
