package br.edu.ifms.EasyComp.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Torneio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String numpartic;
	
	private String link;
	
	private boolean aberto;
	
	@OneToMany(mappedBy = "torneios", fetch = FetchType.EAGER)
	private List<Usuario> usuarios;
	
	@OneToMany
	@JoinTable(name="jogos_torneio",
	   joinColumns = @JoinColumn(name = "torneio_id"),
	   inverseJoinColumns = @JoinColumn(name = "jogos_id"))
	
	private List<Jogos> jogos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNumpartic() {
		return numpartic;
	}

	public void setNumpartic(String numpartic) {
		this.numpartic = numpartic;
	}

	public boolean isAberto() {
		return aberto;
	}

	public void setAberto(boolean aberto) {
		this.aberto = aberto;
	}
	
	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public List<Jogos> getJogos() {
		return jogos;
	}

	public void setJogos(List<Jogos> jogos) {
		this.jogos = jogos;
	}
	
	
}
