package br.edu.ifms.EasyComp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/") 
	public String index(Model model) {
		return "index";
	}
	
	@RequestMapping("/login") 
	public String login() {
		return "login";
	}
	
	@RequestMapping("/asstorneio") 
	public String asstorneio() {
		return "torneios";
	}
	
	@RequestMapping("/sobre") 
	public String sobre() {
		return "sobre";
	}
	
	@RequestMapping("/regtorneio") 
	public String regtorneio() {
		return "regtorneio";
	}

}

