package br.edu.ifms.EasyComp.service;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifms.EasyComp.modelo.Jogos;
import br.edu.ifms.EasyComp.repository.JogosRepository;

@Service
public class JogosServiceImpl implements JogosService {
	@Autowired
	private JogosRepository jogosRepository;

	@Override
	public void apagarJogosPorId(Long id) {
		Jogos jogos = buscarJogosPorId(id);
		jogosRepository.delete(jogos);
	}

	@Override
	public Jogos buscarJogosPorId(Long id) {
		Optional<Jogos> opt = jogosRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new IllegalArgumentException("Jogo com id : " + id + " não existe");
		}
	}

	@Override
	public Jogos buscarJogosPorNome(String nome) {
		Jogos jogos = jogosRepository.findByNome(nome);
		return jogos;
	}

	@Override
	public Jogos gravarJogos(Jogos jogos) {			
				
		return jogosRepository.save(jogos);
	}

	@Override
	public void alterarJogos(Jogos jogos) {
		jogosRepository.save(jogos);
	}

	@Override
	public List<Jogos> listarJogos() {
		List<Jogos> jogos = jogosRepository.findAll();
		return jogos;
	}
}
