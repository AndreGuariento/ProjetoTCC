package br.edu.ifms.EasyComp.service;

import java.util.List;

import br.edu.ifms.EasyComp.modelo.Local;

public interface LocalService {
	public void apagarLocalPorId(Long id);
	public Local buscarLocalPorId(Long id);
	public Local buscarLocalPorNome(String nome);
	public Local gravarLocal(Local local);
	public void alterarLocal(Local local);
	public List<Local> listarLocal();
}
