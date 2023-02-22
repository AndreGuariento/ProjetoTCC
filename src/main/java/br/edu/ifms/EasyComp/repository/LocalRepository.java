package br.edu.ifms.EasyComp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifms.EasyComp.modelo.Local;

public interface LocalRepository extends JpaRepository<Local, Long>  {
	Local findByNome(String nome);
}
