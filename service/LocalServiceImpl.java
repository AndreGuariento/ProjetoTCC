package br.edu.ifms.EasyComp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.ifms.EasyComp.modelo.Local;
import br.edu.ifms.EasyComp.repository.LocalRepository;

@Service
public class LocalServiceImpl implements LocalService {
	@Autowired
	private LocalRepository localRepository;

	@Override
	public void apagarLocalPorId(Long id) {
		Local local = buscarLocalPorId(id);
		localRepository.delete(local);
	}

	@Override
	public Local buscarLocalPorId(Long id) {
		Optional<Local> opt = localRepository.findById(id);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new IllegalArgumentException("Local com id : " + id + " não existe");
		}
	}

	@Override
	public Local buscarLocalPorNome(String nome) {
		Local local = localRepository.findByNome(nome);
		return local;
	}

	@Override
	public Local gravarLocal(Local local) {			
				
		return localRepository.save(local);
	}

	@Override
	public void alterarLocal(Local local) {
		localRepository.save(local);
	}

	@Override
	public List<Local> listarLocal() {
		List<Local> local = localRepository.findAll();
		return local;
	}
}
