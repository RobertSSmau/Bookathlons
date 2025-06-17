package com.bookathlon.repos;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.bookathlon.entities.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {

    @Query("""
    SELECT u FROM Utente u 
    WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%'))
""")
    List<Utente> cercaPerUsername(@Param("username") String username);

    // login 
    Utente findByUsername(String username);
    Utente findByEmail(String email);

    @Query(value = 
    		"SELECT * FROM utente ORDER BY score DESC LIMIT 20", nativeQuery = true)
    List<Utente> trovaClassificaGlobale();
    @Query(value = 
    		"SELECT * FROM utente WHERE id IN (:ids) ORDER BY score DESC LIMIT 20", nativeQuery = true)
    List<Utente> trovaClassificaAmici(@Param("ids") List<Long> amiciIds);
}