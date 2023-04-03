package br.edu.ifms.EasyComp.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.edu.ifms.EasyComp.modelo.Torneio;
import br.edu.ifms.EasyComp.modelo.Usuario;
import br.edu.ifms.EasyComp.service.TorneioService;

@Controller
public class HomeController {
	
	@Autowired
	private TorneioService torneioService;
	
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
	
	@RequestMapping("/sobre") 
	public String sobre() {
		return "sobre";
	}
	
	@RequestMapping("/regtorneio") 
	public String regtorneio(Model model) {
		model.addAttribute("torneio", new Torneio());
		return "regtorneio";
	}
	
	@RequestMapping("/regtorneio2") 
	public String regtorneio2() {
		return "regtorneio2";
	}

}

