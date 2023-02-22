package br.edu.ifms.EasyComp.service;

import java.util.List;

import br.edu.ifms.EasyComp.modelo.Torneio;

public interface TorneioService {
	public void apagarTorneioPorId(Long id);
	public Torneio buscarTorneioPorId(Long id);
	public Torneio buscarTorneioPorNome(String nome);
	public Torneio gravarTorneio(Torneio torneio);
	public void alterarTorneio(Torneio torneio);
	public List<Torneio> listarTorneio();
}
