package com.matienzoShop.celulares.service;

import com.matienzoShop.celulares.model.Celular;
import jakarta.validation.Valid;

import java.util.List;

public interface CelularService {
    List<Celular> listarTodos();
    Celular guardar(Celular celular);
    Celular buscarPorId(Long id);
    void eliminar (Long Id);
    void guardarTodos(List<@Valid Celular> listaCelulares);
}
