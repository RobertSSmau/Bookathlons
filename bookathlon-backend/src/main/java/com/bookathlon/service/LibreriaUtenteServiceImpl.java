package com.bookathlon.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookathlon.entities.LibreriaUtente;
import com.bookathlon.entities.LibreriaUtenteId;
import com.bookathlon.entities.Libro;
import com.bookathlon.entities.Utente;
import com.bookathlon.repos.LibreriaUtenteRepository;
import com.bookathlon.repos.LibroRepository;
import com.bookathlon.repos.UtenteRepository;

/**
 * Questa classe implementa LibreriaUtenteService e gestisce la logica di business per la libreria personale dell'utente. 
 * Permette di visualizzare, aggiungere e rimuovere libri, gestendone gli stati.
 */
@Service 
public class LibreriaUtenteServiceImpl implements LibreriaUtenteService {

    @Autowired 
    private LibreriaUtenteRepository repo;

    @Autowired 
    private UtenteRepository utenteRepo;

    @Autowired 
    private LibroRepository libroRepo;

    /**
     * Recupera tutti i libri presenti nella libreria di un utente specifico.
     * Delega la ricerca al repository.
     */
    @Override
    public List<LibreriaUtente> getLibreriaUtente(Long utenteId) {
        return repo.findByUtenteId(utenteId);
    }

    /**
     * Recupera i libri dalla libreria di un utente in base a uno stato specifico.
     * Delega la ricerca filtrata al repository.
     */
    @Override
    public List<LibreriaUtente> getLibriByStato(Long utenteId, String stato) {
        return repo.findByUtenteIdAndStato(utenteId, stato);
    }

    /**
     * Questo metodo aggiunge un libro alla libreria di un utente, creando e salvando una nuova entry LibreriaUtente dopo aver recuperato gli oggetti Utente e Libro corrispondenti.
     */
    @Override
    public LibreriaUtente aggiungiLibro(Long utenteId, Long libroId, String stato) {
        // Recupera l'utente e il libro dal database o lancia un'eccezione se non trovati.
        Utente utente = utenteRepo.findById(utenteId).orElseThrow();
        Libro libro = libroRepo.findById(libroId).orElseThrow();

        // Crea una nuova istanza di LibreriaUtente.
        LibreriaUtente entry = new LibreriaUtente();

        // Imposta gli attributi della nuova entry.
        entry.setUtente(utente);
        entry.setLibro(libro);
        entry.setStato(stato);
        entry.setDataAggiunta(LocalDate.now()); // Imposta la data di aggiunta corrente.

        if (stato.equals("DA_LEGGERE")) {
            // Prima aggiunta, inizia lettura da oggi
            entry.setDataInizioLettura(LocalDate.now());
            entry.setDataFineLettura(null);
        }

        if (stato.equals("LETTO")) {
            // Se era da leggere, aggiorna la data di inizio se mancante
            if (entry.getDataInizioLettura() == null) {
                entry.setDataInizioLettura(LocalDate.now());
            }

            // Se il libro non era ancora stato letto (no dataFine), aggiorno punteggio
            if (entry.getDataFineLettura() == null) {
                entry.setDataFineLettura(LocalDate.now());

                // Incrementa lo score dell'utente
                int punteggioAttuale = utente.getScore();
                utente.setScore(punteggioAttuale + 1);
                utenteRepo.save(utente);  // salva il nuovo punteggio
            }
        }
        
        // Salva la nuova entry nel database.
        return repo.save(entry);
    }

    /**
     * Rimuove un libro specifico dalla libreria di un utente.
     * Recupera gli oggetti Utente e Libro, crea un ID composto e lo usa per eliminare l'entry.
     */
    @Override
    public void rimuoviLibro(Long utenteId, Long libroId) {
        // Recupera l'utente e il libro dal database o lancia un'eccezione se non trovati.
        Utente utente = utenteRepo.findById(utenteId).orElseThrow();
        Libro libro = libroRepo.findById(libroId).orElseThrow();

        // Crea un oggetto LibreriaUtenteId che rappresenta la chiave primaria composta.
        LibreriaUtenteId id = new LibreriaUtenteId();
        id.setUtente(utente);
        id.setLibro(libro);

        // Elimina l'entry dal database usando l'ID composto.
        repo.deleteById(id);
    }
    
    @Override
    public void iniziaLettura(Long utenteId, Long libroId) {

        Utente utente = utenteRepo.findById(utenteId).orElseThrow();
        Libro libro = libroRepo.findById(libroId).orElseThrow();

        LibreriaUtenteId id = new LibreriaUtenteId();
        id.setUtente(utente);
        id.setLibro(libro);

        // cerco la voce della libreria
        LibreriaUtente entry = repo.findById(id).orElse(null);

        if (entry == null) return;

        if (!"DA_LEGGERE".equals(entry.getStato())) return;

        if (entry.getDataInizioLettura() != null) return;

        entry.setDataInizioLettura(LocalDate.now());

        repo.save(entry);
    }
}