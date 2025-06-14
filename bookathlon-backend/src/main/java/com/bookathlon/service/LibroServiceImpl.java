package com.bookathlon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bookathlon.entities.Libro;
import com.bookathlon.repos.LibroRepository;

/**
 * Implementazione del servizio {@link LibroService} per la gestione dei libri.
 * Fornisce la logica di business per interagire con i dati dei libri,
 * inclusi il recupero, l'aggiunta e la ricerca di libri.
 */
@Service 
public class LibroServiceImpl implements LibroService {

    @Autowired // inserisce l'istanza di LibroRepository.
    private LibroRepository repo;

    /**
     * Recupera una lista di tutti i libri presenti nel sistema.
     * Delega la chiamata al metodo `findAll()` del repository.
     */
    @Override
    public List<Libro> getLibri() {
        return repo.findAll(); // Ritorna tutti i libri trovati nel database.
    }

    /**
     * Recupera un singolo libro tramite il suo ID.
     * Delega la ricerca al metodo `findById()` del repository.
     */
    @Override
    public Libro getLibroById(Long id) {
        return repo.findById(id).orElse(null); // Cerca il libro per ID, ritorna null se non esiste.
    }

    /**
     * Aggiunge un nuovo libro al sistema o aggiorna uno esistente.
     * Delega l'operazione di salvataggio al metodo `save()` del repository.
     */
    @Override
    public Libro addLibro(Libro l) {
        return repo.save(l); // Salva il libro nel database.
    }

    /**
     * Recupera una lista dei primi 5 libri considerati "di tendenza".
     * Attualmente, recupera tutti i libri e limita il risultato ai primi 5.
     * La logica di "tendenza" è qui semplificata.
     */
    @Override
    public List<Libro> getLibriDiTendenza() {
        List<Libro> tutti = repo.findAll(); // Recupera tutti i libri.
        // Limita la lista ai primi 5 elementi.
        return tutti.stream().limit(5).toList();
    }

    /**
     * Esegue una ricerca di libri basata su una parola chiave.
     * Delega la ricerca al metodo `ricercaSQL()` del repository.
     */
    @Override
    public List<Libro> cerca(String keyword) {
        return repo.ricercaSQL(keyword);
		 // Esegue la ricerca tramite la query SQL definita nel repository.
    }
}