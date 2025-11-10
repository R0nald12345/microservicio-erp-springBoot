package com.example.service_erp.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.service_erp.entities.OfertaTrabajo;
import com.example.service_erp.entities.VisualizacionOferta;
import com.example.service_erp.repositories.OfertaTrabajoRepository;
import com.example.service_erp.repositories.VisualizacionOfertaRepository;

@Service
public class VisualizacionOfertaService {

    private final VisualizacionOfertaRepository repository;
    private final OfertaTrabajoRepository ofertaRepository;
    private static final int DEFAULT_LIMIT = 10;

    public VisualizacionOfertaService(VisualizacionOfertaRepository repository, OfertaTrabajoRepository ofertaRepository) {
        this.repository = repository;
        this.ofertaRepository = ofertaRepository;
    }

    public List<VisualizacionOferta> obtenerTodas() {
        return obtenerTodas(DEFAULT_LIMIT);
    }

    public List<VisualizacionOferta> obtenerTodas(Integer limit) {
        int pageSize = (limit != null && limit > 0) ? Math.min(limit, 100) : DEFAULT_LIMIT;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<VisualizacionOferta> page = repository.findAllOrderedByCreatedAtDesc(pageable);
        return page.getContent();
    }

    public List<VisualizacionOferta> obtenerPorOfertaId(UUID ofertaId, Integer limit) {
        int pageSize = (limit != null && limit > 0) ? Math.min(limit, 100) : DEFAULT_LIMIT;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<VisualizacionOferta> page = repository.findByOfertaIdOrderedByCreatedAtDesc(ofertaId, pageable);
        return page.getContent();
    }

    public VisualizacionOferta obtenerPorId(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public VisualizacionOferta crear(String fechaVisualizacion, String origen, UUID ofertaId) {
        OfertaTrabajo oferta = ofertaRepository.findById(ofertaId)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));

        VisualizacionOferta visualizacion = VisualizacionOferta.builder()
                .fechaVisualizacion(fechaVisualizacion)
                .origen(origen)
                .oferta(oferta)
                .build();

        return repository.save(visualizacion);
    }

    public VisualizacionOferta actualizar(UUID id, String fechaVisualizacion, String origen) {
        VisualizacionOferta visualizacion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visualización de oferta no encontrada"));

        if (fechaVisualizacion != null && !fechaVisualizacion.isEmpty()) {
            visualizacion.setFechaVisualizacion(fechaVisualizacion);
        }
        if (origen != null && !origen.isEmpty()) {
            visualizacion.setOrigen(origen);
        }

        return repository.save(visualizacion);
    }

    public void eliminar(UUID id) {
        repository.deleteById(id);
    }
}
