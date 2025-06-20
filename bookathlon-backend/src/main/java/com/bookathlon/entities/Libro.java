package com.bookathlon.entities;

import java.time.LocalDate;

// Importa le annotazioni JPA che permettono di collegare la classe Java a una tabella del database.
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
  // Indica a Spring che questa classe rappresenta una tabella del database
@Table(name="libro")
  // Specifica che è collegata alla tabella 'libro' nel database

public class Libro {
	
// Ogni attributo è mappato su una colonna della tabella libro.Spring e JPA si occuperà automaticamente della conversione
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String isbn;
	private String titolo;
	private String autore;
	private String genere;
	private LocalDate data_pubblicazione;
	private String url_copertina;
	private String descrizione;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitolo() {
		return titolo;
	}
	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}
	public String getAutore() {
		return autore;
	}
	public void setAutore(String autore) {
		this.autore = autore;
	}
	public String getGenere() {
		return genere;
	}
	public void setGenere(String genere) {
		this.genere = genere;
	}
	public LocalDate getData_pubblicazione() {
		return data_pubblicazione;
	}
	public void setData_pubblicazione(LocalDate data_pubblicazione) {
		this.data_pubblicazione = data_pubblicazione;
	}
	public String getUrl_copertina() {
		return url_copertina;
	}
	public void setUrl_copertina(String url_copertina) {
		this.url_copertina = url_copertina;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
	
	
	
	
	



	
	
	}
	
