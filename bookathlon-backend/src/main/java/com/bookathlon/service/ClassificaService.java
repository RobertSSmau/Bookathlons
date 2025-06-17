package com.bookathlon.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import com.bookathlon.entities.Utente;



public interface ClassificaService {
	
	List<Utente> getClassificaGlobale();
	List<Utente> getClassificaAmici(UserDetails userDetails);

	
}
