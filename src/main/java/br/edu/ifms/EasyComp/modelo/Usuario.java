package br.edu.ifms.EasyComp.modelo;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(min = 3, message = "O nome deve ter no mínimo 3 carateres")
	private String username;
	
	@Email(message = "Email inválido")
	private String email;
		
	@NotEmpty(message = "A senha deve ser informada")
	@Size(min = 3, message = "A senha deve ter no mínimo 3 caracteres")
	private String password;	
	
	@NotEmpty(message = "O login deve ser informado")
	@Size(min = 4, message = "O login deve ter no mínimo 4 caracteres")
	private String login;
	
	private boolean ativo = true;	
	
	//private int tornvenc;
	
	//private int tornpart;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="usuario_papel",
			   joinColumns = @JoinColumn(name = "usuario_id"),
			   inverseJoinColumns = @JoinColumn(name = "papel_id"))
	
	private List<Papel> papeis;
	
	@ManyToMany
	@JoinTable(name="usuario_torneio",
	   joinColumns = @JoinColumn(name = "usuario_id"),
	   inverseJoinColumns = @JoinColumn(name = "torneio_id"))
	
	private List<Torneio> torneios;
	
	@OneToMany(mappedBy = "usuario")
	private List<Torneio> torneiospes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public List<Papel> getPapeis() {
		return papeis;
	}

	public void setPapeis(List<Papel> papeis) {
		this.papeis = papeis;
	}
	
	public List<Torneio> getTorneios() {
		return torneios;
	}

	public void setTorneios(List<Torneio> torneios) {
		this.torneios = torneios;
	}

	public List<Torneio> getTorneiospes() {
		return torneiospes;
	}

	public void setTorneiospes(List<Torneio> torneiospes) {
		this.torneiospes = torneiospes;
	}
	
	public void removeUserTorneio(Torneio torneio){
		   this.torneios.remove(torneio);
		   torneio.getUsuarios().remove(this);
		}
	
	
	
	
}
