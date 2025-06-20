package com.bookathlon.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Rappresenta l'entità 'LibreriaUtente' nel database,
 * che gestisce la relazione molti-a-molti tra Utente e Libro,
 * con attributi aggiuntivi come lo stato del libro per l'utente.
 * Utilizza una chiave composta definita da LibreriaUtenteId.
 */
@Entity 
// Indica che questa classe è un'entità JPA.
@Table(name = "libreria_utente")
 // Specifica il nome della tabella nel database.
@IdClass(LibreriaUtenteId.class) 
// Definisce la classe che rappresenta la chiave primaria composta.
public class LibreriaUtente {

    @Id 
    @ManyToOne 
    @JoinColumn(name= "utente_id")
    private Utente utente; 
    @Id 
    @ManyToOne 
    @JoinColumn(name = "libro_id") 
    private Libro libro; 
    @Column(nullable = false) 
    private String stato; 

    @Column(name = "data_aggiunta") 
    private LocalDate dataAggiunta = LocalDate.now(); 
    
    @Column(name = "data_inizio_lettura")
    private LocalDate dataInizioLettura;

    @Column(name = "data_fine_lettura")
    private LocalDate dataFineLettura;
    
    /**
     * Costruttore di default.
     */
    public LibreriaUtente() {}

    // Getters e Setters 
    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public LocalDate getDataAggiunta() {
        return dataAggiunta;
    }

    public void setDataAggiunta(LocalDate dataAggiunta) {
        this.dataAggiunta = dataAggiunta;
    }

	public LocalDate getDataInizioLettura() {
		return dataInizioLettura;
	}

	public void setDataInizioLettura(LocalDate dataInizioLettura) {
		this.dataInizioLettura = dataInizioLettura;
	}

	public LocalDate getDataFineLettura() {
		return dataFineLettura;
	}

	public void setDataFineLettura(LocalDate dataFineLettura) {
		this.dataFineLettura = dataFineLettura;
	}
    
    
}












