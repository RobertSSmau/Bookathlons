package com.bookathlon.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.bookathlon.entities.Amicizia;
import com.bookathlon.entities.Utente;
import com.bookathlon.repos.UtenteRepository;

@Service
public class ClassificaServiceImpl implements ClassificaService {

	@Autowired
    private UtenteRepository utenteRepo;

    @Autowired
    private AmiciziaService amiciziaService;
	
	@Override
	public List<Utente> getClassificaGlobale() {
		return utenteRepo.trovaClassificaGlobale();
	}

	@Override
	public List<Utente> getClassificaAmici(UserDetails userDetails) {
	    Utente utente = utenteRepo.findByUsername(userDetails.getUsername());
	    List<Amicizia> amicizie = amiciziaService.getAmici(utente.getId());
	    List<Long> ids = new ArrayList<>();

	    for (int i = 0; i < amicizie.size(); i++) {
	        
	    	Amicizia a = amicizie.get(i);
	        
	    	Long idUtente1 = a.getUtente1();
	        Long idUtente2 = a.getUtente2();
	        Long altroId;

	        if (idUtente1.equals(utente.getId())) {
	            altroId = idUtente2;
	        } else {
	            altroId = idUtente1;
	        }

	        ids.add(altroId);
	    }

	    if (ids.isEmpty()) {
	        return new ArrayList<>();
	    }

	    return utenteRepo.trovaClassificaAmici(ids);
	}

}
