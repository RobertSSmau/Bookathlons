package com.bookathlon.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity   
 
@Table(name = "utente")   

public class Utente {

    @Id  
   
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
  
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Lo username è obbligatorio")
    @Size(min = 3, max = 50, message = "Lo username deve avere tra 3 e 50 caratteri")
    @Column(nullable = false, unique = true)// Lo username è un campo obbligatorio e deve essere unico nel DB
    private String username;

    @NotBlank(message = "La password è obbligatoria")
    @Pattern(
    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()\\[\\]{}:;',.?/~$^+=<>]).{8,}$",
    message = "La password deve contenere almeno 8 caratteri, un numero, una lettera maiuscola, una minuscola e un carattere speciale"
    )
    @Column(nullable = false)  //La password è obbligatoria e deve essere cifrata prima di salvarla nel DB
    private String password;

    @Transient // Non verrà salvato nel DB
    @NotBlank(message = "La conferma della password è obbligatoria")
    private String confermaPassword;

    
    @NotBlank(message = "L'email è obbligatoria")
    @Email(message = "Email non valida")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)  
    private String ruolo;
    
    @Column(nullable = false)
    private int score;



    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;

    }
    
    public String getConfermaPassword() {
        return confermaPassword;
    }

    public void setConfermaPassword(String confermaPassword) {
        this.confermaPassword = confermaPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

    

}
