package br.edu.ifms.EasyComp.service;

import java.util.List;

import br.edu.ifms.EasyComp.modelo.Jogos;

public interface JogosService {
	public void apagarJogosPorId(Long id);
	public Jogos buscarJogosPorId(Long id);
	public Jogos buscarJogosPorNome(String nome);
	public Jogos gravarJogos(Jogos jogos);
	public void alterarJogos(Jogos jogos);
	public List<Jogos> listarJogos();
}
