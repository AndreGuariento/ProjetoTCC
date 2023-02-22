package br.edu.ifms.EasyComp.service;

import java.util.List;

import br.edu.ifms.EasyComp.modelo.Papel;

public interface PapelService {
	public Papel buscarPapelPorId(Long id);
	public Papel buscarPapel(String papel);
	public List<Papel> listarPapel();
}
