package com.bookathlon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookathlon.entities.Libro;
import com.bookathlon.service.LibroService;

@RestController
public class LibroREST {

	@Autowired
	private LibroService service;
	
	@GetMapping("api/libri")
	public List<Libro> getLibri(){
		return service.getLibri();
	}
	
	@PostMapping("api/libri")
	public Libro addLibro(@RequestBody Libro l) {
		return service.addLibro(l);
		}
	
}
