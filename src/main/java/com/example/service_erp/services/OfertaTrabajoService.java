package com.example.service_erp.services;

import com.example.service_erp.entities.Empresa;
import com.example.service_erp.entities.OfertaTrabajo;
import com.example.service_erp.repositories.EmpresaRepository;
import com.example.service_erp.repositories.OfertaTrabajoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OfertaTrabajoService {

    private final OfertaTrabajoRepository repository;
    private final EmpresaRepository empresaRepository;
    private static final int DEFAULT_LIMIT = 10;

    public OfertaTrabajoService(OfertaTrabajoRepository repository, EmpresaRepository empresaRepository) {
        this.repository = repository;
        this.empresaRepository = empresaRepository;
    }

    public List<OfertaTrabajo> obtenerTodas() {
        return obtenerTodas(DEFAULT_LIMIT);
    }

    public List<OfertaTrabajo> obtenerTodas(Integer limit) {
        int pageSize = (limit != null && limit > 0) ? Math.min(limit, 100) : DEFAULT_LIMIT; // Máximo 100
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<OfertaTrabajo> page = repository.findAllOrderedByCreatedAtDesc(pageable);
        return page.getContent();
    }

    public List<OfertaTrabajo> obtenerPorEmpresaId(UUID empresaId, Integer limit) {
        int pageSize = (limit != null && limit > 0) ? Math.min(limit, 100) : DEFAULT_LIMIT;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<OfertaTrabajo> page = repository.findByEmpresaIdOrderedByCreatedAtDesc(empresaId, pageable);
        return page.getContent();
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
