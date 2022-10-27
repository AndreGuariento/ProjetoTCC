package br.edu.ifms.EasyComp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifms.EasyComp.modelo.Papel;

public interface PapelRepository extends JpaRepository<Papel, Long> {
	Papel findByPapel(String papel);
}
