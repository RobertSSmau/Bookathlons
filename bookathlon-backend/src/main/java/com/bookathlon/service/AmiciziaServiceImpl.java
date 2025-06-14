package com.bookathlon.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bookathlon.entities.Amicizia;
import com.bookathlon.repos.AmiciziaRepo;
/**
 * Implementazione del servizio {@link AmiciziaService} per la gestione delle relazioni di amicizia.
 * Fornisce la logica di business per recuperare lo stato delle amicizie e gestire le richieste.
 */
@Service 
public class AmiciziaServiceImpl implements AmiciziaService {

	private AmiciziaRepo repo;
	
	@Override
    public List<Amicizia> getAmici(Long userId) {
        return repo.trovaAmiciAccettati(userId);
    }

    @Override
    public List<Amicizia> getRichiesteRicevute(Long userId) {
        return repo.trovaRichiesteRicevute(userId);
    }

    @Override
    public List<Amicizia> getRichiesteInviate(Long userId) {
        return repo.trovaRichiesteInviate(userId);
    }

    @Override
    public void inviaRichiesta(Long daUtenteId, Long aUtenteId) {
        Amicizia a = new Amicizia();
        a.setUtente1(daUtenteId);
        a.setUtente2(aUtenteId);
        a.setStato("PENDING");
        repo.save(a);
    }

	@Override
	public void accettaRichiesta(Long daUtenteId, Long aUtenteId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void rifiutaRichiesta(Long daUtenteId, Long aUtenteId) {
		// TODO Auto-generated method stub

	}

}
