package br.edu.ifms.EasyComp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.ifms.EasyComp.modelo.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Usuario findByLogin(String login);
}
