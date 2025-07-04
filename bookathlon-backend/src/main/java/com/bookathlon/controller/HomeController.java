package com.bookathlon.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import com.bookathlon.dto.UtenteDTO;
import com.bookathlon.entities.Commento;
import com.bookathlon.entities.LibreriaUtente;
import com.bookathlon.entities.Libro;
import com.bookathlon.entities.Utente;
import com.bookathlon.repos.UtenteRepository;
import com.bookathlon.service.CommentoService;
import com.bookathlon.service.LibreriaUtenteService;
import com.bookathlon.service.LibroService;
import com.bookathlon.service.LikeCommentoService;

/**
 * Controller per la gestione della homepage e della funzionalità di ricerca libri.
 * Gestisce le richieste GET per la visualizzazione della homepage e dei risultati di ricerca.
 */
@Controller
public class HomeController {

    @Autowired
    private LibroService libroService;
    
    @Autowired
    private UtenteRepository utenteRepo; //errore , uso repo invece che un service
    
    @Autowired
    private LibreriaUtenteService libreriaService;
    
    @Autowired
    private CommentoService commServ;
    
    @Autowired
    private LikeCommentoService likeServ;
    

 /**
     * Gestisce la richiesta per la homepage dell'applicazione.
     * Recupera i libri di tendenza e tutti i libri, li raggruppa per genere
     * e li aggiunge al modello per la visualizzazione sulla pagina "home".
     */
    @GetMapping("/")
    public String homePage(Model m, @AuthenticationPrincipal UserDetails userDetails) {
        // Recupera i libri più letti (i primi 5 almeno)
        List<Libro> libriTendenza = libroService.getLibriDiTendenza();

        // Recupera la lista di tutti i libri
        List<Libro> tuttiLibri = libroService.getLibri();

        // HashMap per raggruppare i libri per genere per le card della home
        Map<String, List<Libro>> libriPerGenere = new HashMap<>();
        for (Libro libro : tuttiLibri) {
            String genere = libro.getGenere();
            if (genere != null) {
                libriPerGenere.putIfAbsent(genere, new ArrayList<>());
                libriPerGenere.get(genere).add(libro);
            }
        }

        // Passaggio dati a Thymeleaf
        m.addAttribute("tendenze", libriTendenza);
        m.addAttribute("libriPerGenere", libriPerGenere);

        if (userDetails != null) {
            // Recupera l'utente loggato
            String username = userDetails.getUsername();
            Utente utente = utenteRepo.findByUsername(username);
            
            // Recupera la libreria dell'utente
            List<LibreriaUtente> libreria = libreriaService.getLibreriaUtente(utente.getId());

            // Estrai gli ID dei libri nella libreria
            List<Long> idLibriUtente = new ArrayList<>();
            for (LibreriaUtente entry : libreria) {
                Long idLibro = entry.getLibro().getId();
                idLibriUtente.add(idLibro);
            }

            // Passa la lista al template
            m.addAttribute("idLibriUtente", idLibriUtente);
        }
        
        return "home";
         // Ritorna il nome della vista "home".
    }

    /**
     * Gestisce la richiesta di ricerca libri tramite una parola chiave.
     * Esegue una ricerca sui libri basandosi sulla parola chiave fornita
     * e aggiunge i risultati al modello per la visualizzazione sulla pagina "risultati-filtrati".
     */
    @GetMapping("/cerca-per-titolo")
    public String cercaPerTitolo(@RequestParam String titolo, Model m) {
        List<Libro> risultati = libroService.cercaTitolo(titolo);
        m.addAttribute("filtrati", risultati);
        return "risultati-filtrati";
    }
    
    @GetMapping("/cerca-per-autore")
    public String cercaPerAutore(@RequestParam String autore, Model m) {
        List<Libro> risultati = libroService.cercaAutore(autore);
        m.addAttribute("filtrati", risultati);
        return "risultati-filtrati";
    }
    
    @GetMapping("/libro/{id}")
    public String mostraDettaglioLibro(@PathVariable("id") Long id, Model m,
                                       @AuthenticationPrincipal UserDetails userDetails) {

        Libro libro = libroService.getLibroById(id);
        m.addAttribute("libro", libro);

        List<Commento> commenti = commServ.trovaPerLibro(id);
        m.addAttribute("commenti", commenti);
        
        List<UtenteDTO> autoriCommenti = new ArrayList<>();
        for (Commento c : commenti) {
            Utente u = utenteRepo.findById(c.getUtenteId()).orElse(null);
            if (u != null) {
                autoriCommenti.add(new UtenteDTO(u.getId(), u.getUsername(), u.getScore()));
            } else {
                autoriCommenti.add(new UtenteDTO(0L, "Utente sconosciuto"));
            }
        }
        m.addAttribute("autoriCommenti", autoriCommenti);

        if (userDetails != null) {
            Utente utente = utenteRepo.findByUsername(userDetails.getUsername());
            m.addAttribute("utenteLoggatoId", utente.getId());
        }

        m.addAttribute("likeService", likeServ); 
        return "dettaglio-libro";
    }
    
    @PostMapping("/libro/commenta")
    public String salvaCommento(@RequestParam Long libroId,
                                @RequestParam String contenuto,
                                @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        Utente utente = utenteRepo.findByUsername(userDetails.getUsername());

        Commento nuovo = new Commento();
        nuovo.setLibroId(libroId);
        nuovo.setUtenteId(utente.getId());
        nuovo.setContenuto(contenuto);

        commServ.salva(nuovo);

        return "redirect:/libro/" + libroId;
    }
    
    @PostMapping("/libro/commento/like")
    public String toggleLike(@RequestParam Long commentoId,
                             @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) return "redirect:/login";

        Utente utente = utenteRepo.findByUsername(userDetails.getUsername());
        Long utenteId = utente.getId();

        if (likeServ.haGiaMessoLike(commentoId, utenteId)) {
            likeServ.rimuoviLike(commentoId, utenteId);
        } else {
            likeServ.mettiLike(commentoId, utenteId);
        }

        Commento commento = commServ.getById(commentoId);
        if (commento == null) 
        	return "redirect:/";

        Long libroId = commento.getLibroId();
        return "redirect:/libro/" + libroId;
    }
}