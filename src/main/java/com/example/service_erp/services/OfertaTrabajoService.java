package com.example.service_erp.services;

import com.example.service_erp.entities.Empresa;
import com.example.service_erp.entities.OfertaTrabajo;
import com.example.service_erp.repositories.EmpresaRepository;
import com.example.service_erp.repositories.OfertaTrabajoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OfertaTrabajoService {

    private final OfertaTrabajoRepository repository;
    private final EmpresaRepository empresaRepository;

    public OfertaTrabajoService(OfertaTrabajoRepository repository, EmpresaRepository empresaRepository) {
        this.repository = repository;
        this.empresaRepository = empresaRepository;
    }

    public List<OfertaTrabajo> obtenerTodas() {
        return repository.findAll();
    }

    public OfertaTrabajo obtenerPorId(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public OfertaTrabajo crear(String titulo, String descripcion, Double salario, String ubicacion,
                               String requisitos, String fechaPublicacion, UUID empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        OfertaTrabajo oferta = OfertaTrabajo.builder()
                .titulo(titulo)
                .descripcion(descripcion)
                .salario(salario)
                .ubicacion(ubicacion)
                .requisitos(requisitos)
                .fechaPublicacion(fechaPublicacion)
                .empresa(empresa)
                .build();

        return repository.save(oferta);
    }

    public void eliminar(UUID id) {
        repository.deleteById(id);
    }
}
