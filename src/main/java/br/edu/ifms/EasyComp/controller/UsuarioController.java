package br.edu.ifms.EasyComp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.ifms.EasyComp.modelo.Papel;
import br.edu.ifms.EasyComp.modelo.Usuario;
import br.edu.ifms.EasyComp.modelo.Jogos;
import br.edu.ifms.EasyComp.modelo.Torneio;
import br.edu.ifms.EasyComp.repository.PapelRepository;
import br.edu.ifms.EasyComp.repository.UsuarioRepository;
import br.edu.ifms.EasyComp.repository.JogosRepository;
import br.edu.ifms.EasyComp.repository.TorneioRepository;
import br.edu.ifms.EasyComp.service.JogosService;
import br.edu.ifms.EasyComp.service.PapelService;
import br.edu.ifms.EasyComp.service.TorneioService;
import br.edu.ifms.EasyComp.service.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	/*
	@Autowired
	private PapelRepository papelRepository;
	*/
	@Autowired
	private PapelService papelService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private JogosService jogosService;
	
	@Autowired
	private TorneioService torneioService;
	
	
	/**
	 * Método que verifica qual papel o usuário tem na aplicação 
	 * */
	private boolean temAutorizacao(Usuario usuario, String papel) {
		for (Papel pp : usuario.getPapeis()) {
			if (pp.getPapel().equals(papel)) {
				return true;
			}
	    }
		return false;
	}
	
		
	@GetMapping("/novo")
	public String adicionarUsuario(Model model) {
		model.addAttribute("usuario", new Usuario());
		return "/registro";
	}
	
	@PostMapping("/salvar")
	public String salvarUsuario(@Valid Usuario usuario, BindingResult result, 
				Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return "/registro";
		}
		
		Usuario usr = usuarioService.buscarUsuarioPorLogin(usuario.getLogin());
		if (usr != null) {
			model.addAttribute("loginExiste", "Login já existe cadastrado");
			return "/registro";
		}
		
		usuarioService.gravarUsuario(usuario);
		attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
		return "redirect:/login";
	}
	
	@RequestMapping("/admin/listar")
	public String listarUsuario(Model model) {
		List<Usuario> lista = usuarioService.listarUsuario(); 
		model.addAttribute("usuarios", lista);		
		return "/auth/admin/admin-listar-usuario";		
	}
	
	@RequestMapping("/admin/listarUserTorneioId/{id}")
	public String listarUsuarioTorneioID(Model model, @PathVariable("id") long id) {		
		Torneio torneio = torneioService.buscarTorneioPorId(id);
	    model.addAttribute("torneio", torneio);
		return "/auth/admin/admin-listar-usuariotorneio";		
	}
	
	@RequestMapping("/admin/listarUserTorn")
	public String listarUsuarioTorneio(Model model) {
		List<Torneio> lista = torneioService.listarTorneio(); 
		model.addAttribute("torneios", lista);	
		return "/auth/admin/admin-listar-torneios";		
	}
	
	@RequestMapping("/user/listarUserTornSep")
	public String listarUsuarioTorneio(Model model, @CurrentSecurityContext(expression = "authentication.name") String login) {
		Usuario usuario = usuarioService.buscarUsuarioPorLogin(login);
		/* List<Torneio> lista = torneioService.listarTorneio(); */
		List<Torneio> lista = usuario.getTorneiospes();
		System.out.println("-------------" + lista.size());
		model.addAttribute("torneios", lista);	
		return "/auth/user/user-listar-torneios";		
	}
	
	
	@GetMapping("/inscrever/{id}")
	public String inscreverUsuario(@PathVariable("id") long id, Model model, @CurrentSecurityContext(expression = "authentication.name") String login) {
		Usuario usuario = usuarioService.buscarUsuarioPorLogin(login);
		Torneio torneio = torneioService.buscarTorneioPorId(id);
		usuario.getTorneios().add(torneio);
		usuarioService.gravarUsuario(usuario);
	    return "redirect:/asstorneio";
	}
	
	@GetMapping("/admin/apagar/{id}")
	public String deleteUser(@PathVariable("id") long id, Model model) {
		usuarioService.apagarUsuarioPorId(id);
	    return "redirect:/usuario/admin/listar";
	}
	
	@GetMapping("/editar/{id}")
	public String editarUsuario(@PathVariable("id") long id, Model model) {
		Usuario usuario = usuarioService.buscarUsuarioPorId(id);
	    model.addAttribute("usuario", usuario);
	    return "/auth/user/user-alterar-usuario";
	}
	
	@PostMapping("/editar/{id}")
	public String editarUsuario(@PathVariable("id") long id, 
			@Valid Usuario usuario, BindingResult result) {
		if (result.hasErrors()) {
	    	usuario.setId(id);
	        return "/auth/user/user-alterar-usuario";
	    }
	    usuarioService.alterarUsuario(usuario);
	    return "redirect:/usuario/admin/listar";
	}
		
	@GetMapping("/editarPapel/{id}")
	public String selecionarPapel(@PathVariable("id") long id, Model model) {
		Usuario usuario = usuarioService.buscarUsuarioPorId(id);
	    model.addAttribute("usuario", usuario);
	    List<Papel> papeis = papelService.listarPapel();
	    model.addAttribute("listaPapeis", papeis);
	    
	    return "/auth/admin/admin-editar-papel-usuario";
	}
	
	@PostMapping("/editarPapel/{id}")
	public String atribuirPapel(@PathVariable("id") long idUsuario, 
								@RequestParam(value = "pps", required=false) int[] pps, 
								Usuario usuario, 
								RedirectAttributes attributes) {
		if (pps == null) {
			usuario.setId(idUsuario);
			attributes.addFlashAttribute("mensagem", "Pelo menos um papel deve ser informado");
			return "redirect:/usuario/editarPapel/"+idUsuario;
		} else {
			usuarioService.atribuirPapelParaUsuario(idUsuario, pps, usuario.isAtivo());		
		}		
	    return "redirect:/usuario/admin/listar";
	}
	@RequestMapping("/admin/listarJogos")
	public String listarJogo(Model model) {
		List<Jogos> lista = jogosService.listarJogos(); 
		model.addAttribute("jogos", lista);		
		return "/auth/admin/admin-listar-jogos";		
	}
	
	@GetMapping("/admin/apagarJogos/{id}")
	public String deleteJogo(@PathVariable("id") long id, Model model) {
		jogosService.apagarJogosPorId(id);
	    return "redirect:/usuario/admin/listarJogos";
	}
	
	@GetMapping("/editarJogos/{id}")
	public String editarJ(@PathVariable("id") long id, Model model) {
		Jogos jogos = jogosService.buscarJogosPorId(id);
	    model.addAttribute("jogos", jogos);
	    return "/auth/user/user-alterar-jogos";
	}
	
	@PostMapping("/editarJogos/{id}")
	public String editarJogos(@PathVariable("id") long id, 
			@Valid Jogos jogos, BindingResult result) {
		if (result.hasErrors()) {
	    	jogos.setId(id);
	        return "/auth/user/user-alterar-jogos";
	    }
	    jogosService.alterarJogos(jogos);
	    return "redirect:/usuario/admin/listarJogos";
	}
	
	@GetMapping("/admin/novoJogo")
	public String adicionarJogo(Model model) {
		model.addAttribute("jogos", new Jogos());
		return "/auth/admin/admin-novo-jogo";
	}
	
	@PostMapping("/admin/salvarJogo")
	public String salvarJogo(@Valid Jogos jogos, BindingResult result, 
				Model model, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			return "/auth/admin/admin-novo-jogo";
		}	
		jogosService.gravarJogos(jogos);
		attributes.addFlashAttribute("mensagem", "Jogo salvo com sucesso!");
		return "redirect:/usuario/admin/listarJogos";
	}
	
	@GetMapping("/novoTorneio")
	public String adicionarTorneio(Model model) {
		List<Jogos> jogos = jogosService.listarJogos();
		model.addAttribute("listajogos", jogos);
		model.addAttribute("torneio", new Torneio());
		return "/regtorneio";
	}
	
	@PostMapping("/salvarTorneio")
	public String salvarTorneio(@Valid Torneio torneio, BindingResult result, 
				Model model, RedirectAttributes attributes, @CurrentSecurityContext(expression = "authentication.name") String login) {
		
		if (result.hasErrors()) {
			return "/regtorneio";
		}
		Usuario usuario = usuarioService.buscarUsuarioPorLogin(login);
		
//		Torneio tor = torneioService.buscarTorneioPorId(torneio.getId());
//		if (tor != null) {
//			model.addAttribute("torneioExiste", "Torneio já cadastrado");
//			return "/regtorneio";
//		}
		torneio.setUsuario(usuario);
		torneioService.gravarTorneio(torneio);
		attributes.addFlashAttribute("mensagem", "Torneio salvo com sucesso!");
		return "redirect:/asstorneio";
	}
	
	@RequestMapping("/user/listarTorneio")
	public String listarTorneio(Model model) {
		List<Torneio> torneio = torneioService.listarTorneio(); 
		model.addAttribute("torneios", torneio);		
		return "/torneios";		
	}
	
//	@PostMapping("user/atribuirJogo/{id}")
//	public String atribuirJogo(@PathVariable("id") long idTorneio, 
//								@RequestParam(value = "jgs", required=false) int[] jgs, 
//								Torneio torneio, 
//								RedirectAttributes attributes) {
//		if (jgs == null) {
//			torneio.setId(idTorneio);
//			attributes.addFlashAttribute("mensagem", "Pelo menos um jogo deve ser informado");
//			return "redirect:/usuario/editarJogos/"+idTorneio;
//		} else {
//			torneioService.atribuirJogoParaTorneio(idTorneio, jgs, torneio.isAberto());		
//		}		
//	    return "redirect:/usuario/admin/listar";
//	}
	
	@GetMapping("/user/apagarUserTorn/{id}/{idtorn}")
	public String deleteUserTorn(@PathVariable("id") long id, @PathVariable("idtorn") long idtorn, Model model) {
		Usuario usuario = usuarioService.buscarUsuarioPorId(id);
		Torneio torneio = torneioService.buscarTorneioPorId(idtorn);
		usuario.removeUserTorneio(torneio);
		usuarioRepository.flush();
	    return "redirect:/usuario/mod/listarUserTorneioId/" + idtorn;
}
//	
//	@GetMapping("/editarTorneio/{id}")
//	public String editarTorneio(@PathVariable("id") long id, Model model) {
//		Torneio torneio = torneioService.buscarTorneioPorId(id);
//	    model.addAttribute("torneio", torneio);
//	    return "???";
//	}
//	
//	@PostMapping("/editarTorneio/{id}")
//	public String editarTorneio(@PathVariable("id") long id, 
//			@Valid Torneio torneio, BindingResult result) {
//		if (result.hasErrors()) {
//	    	torneio.setId(id);
//	        return "???";
//	    }
//	    torneioService.alterarTorneio(torneio);
//	    return "???";
//	}
}




