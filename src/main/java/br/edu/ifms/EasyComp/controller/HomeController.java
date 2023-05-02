package br.edu.ifms.EasyComp.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifms.EasyComp.modelo.Torneio;
import br.edu.ifms.EasyComp.modelo.Usuario;
import br.edu.ifms.EasyComp.service.TorneioService;
import br.edu.ifms.EasyComp.service.UsuarioService;

@Controller
public class HomeController {
	
	@Autowired
	private TorneioService torneioService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@RequestMapping("/") 
	public String index(Model model) {
		return "index";
	}
	
	@RequestMapping("/login") 
	public String login() {
		return "login";
	}
	
	@RequestMapping("/asstorneio") 
	public String asstorneio(Model model) {
//		HashMap<String, String> map = new HashMap<>(); 
//		map.put("1", "/img/864431.jpg"); map.put("2", "/img/TrueTF2.jpg");
//		map.put("3", "/img/thumb-1920-927337.png"); 
		List<Torneio> lista = torneioService.listarTorneio(); 
		model.addAttribute("torneios", lista);	
//		model.addAttribute("map", map);
		return "torneios";
	}
	
	@RequestMapping("/asstorneioinsc") 
	public String asstorneioinsc(Model model, @CurrentSecurityContext(expression = "authentication.name") String login) {
//		HashMap<String, String> map = new HashMap<>(); 
//		map.put("1", "/img/864431.jpg"); map.put("2", "/img/TrueTF2.jpg");
//		map.put("3", "/img/thumb-1920-927337.png"); 
		Usuario usuario = usuarioService.buscarUsuarioPorLogin(login);
		List<Torneio> lista = usuario.getTorneios();
		model.addAttribute("torneios", lista);	
//		model.addAttribute("map", map);
		return "auth/user/torneiosinsc";
	}
	
	@RequestMapping("/sobre") 
	public String sobre() {
		return "sobre";
	}
	
	@RequestMapping("/regtorneio") 
	public String regtorneio(Model model) {
		model.addAttribute("torneio", new Torneio());
		return "regtorneio";
	}

}

