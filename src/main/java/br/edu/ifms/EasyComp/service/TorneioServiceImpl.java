package br.edu.ifms.EasyComp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifms.EasyComp.modelo.Jogos;
import br.edu.ifms.EasyComp.modelo.Papel;
import br.edu.ifms.EasyComp.modelo.Torneio;
import br.edu.ifms.EasyComp.modelo.Usuario;
import br.edu.ifms.EasyComp.repository.TorneioRepository;

@Service
public class TorneioServiceImpl implements TorneioService {
	@Autowired
	private TorneioRepository torneioRepository;
	
	@Autowired
	private JogosService jogosService;

	@Override
	public void apagarTorneioPorId(Long id) {
		Torneio torneio = buscarTorneioPorId(id);
		torneioRepository.delete(torneio);
	}

	@Override
	public Torneio buscarTorneioPorId(Long id) {
		Optional<Torneio> opt = torneioRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new IllegalArgumentException("Torneio com id : " + id + " não existe");
		}
	}

	@Override
	public Torneio buscarTorneioPorNome(String nome) {
		Torneio torneio = torneioRepository.findByNome(nome);
		return torneio;
	}

	@Override
	public Torneio gravarTorneio(Torneio torneio) {			
				
		return torneioRepository.save(torneio);
	}

	@Override
	public void alterarTorneio(Torneio torneio) {
		torneioRepository.save(torneio);
	}

	@Override
	public List<Torneio> listarTorneio() {
		List<Torneio> torneio = torneioRepository.findAll();
		return torneio;
	}
	
	@Override
	public void atribuirJogoParaTorneio(long idTorneios, int[] idsJogos, boolean isAberto) {
		List<Jogos> jogos = new ArrayList<Jogos>();			 
		for (int i = 0; i < idsJogos.length; i++) {
			long idJogos = idsJogos[i];
			Jogos jogo = jogosService.buscarJogosPorId(idJogos);
			jogos.add(jogo);
		}
		Torneio torneio = buscarTorneioPorId(idTorneios);
		torneio.setJogos(jogos);
		torneio.setAberto(isAberto);
		alterarTorneio(torneio);		
	}
}
