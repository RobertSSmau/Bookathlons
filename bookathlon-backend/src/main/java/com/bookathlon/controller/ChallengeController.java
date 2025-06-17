package com.bookathlon.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bookathlon.service.ClassificaService;


@Controller
public class ChallengeController {

	@Autowired
	private ClassificaService classificaService;
	
	@GetMapping("/challenge")
    public String challengePage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("classificaGlobale", classificaService.getClassificaGlobale());
        model.addAttribute("classificaAmici", classificaService.getClassificaAmici(userDetails));
        return "challenge";
    }
	
}
	

