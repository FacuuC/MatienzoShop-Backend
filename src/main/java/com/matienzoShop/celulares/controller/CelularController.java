package com.matienzoShop.celulares.controller;

import com.matienzoShop.celulares.model.Celular;
import com.matienzoShop.celulares.service.CelularService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins= "http://localhost:5173")
@RestController
@RequestMapping("/celulares")
public class CelularController {

    private final CelularService celularService;

    public CelularController (CelularService celularService){
        this.celularService = celularService;
    }

    @GetMapping
    public List<Celular> listarCelulares(){
        return celularService.listarTodos();
    }

    @GetMapping("/{id}")
    public Celular obtenerCelular (@PathVariable Long id){
        return celularService.buscarPorId(id);
    }

    @PostMapping("/añadirCel")
    public ResponseEntity<?> guardarCelular(@Valid @RequestBody Celular celular){
        Celular celularNuevo = celularService.guardar(celular);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "¡Celular cargado correctamente!");
        response.put("celular", celularNuevo);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/cargaMasiva")
    public ResponseEntity<?> guardarListaCelulares (@RequestBody List<@Valid Celular> listaCelulares){
        celularService.guardarTodos(listaCelulares);

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Se han cargado " + listaCelulares.size() + " celulares exitosamente.");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/eliminar/{id}")
    public void eliminarCelular (@PathVariable Long id){
        celularService.eliminar(id);
    }
}
