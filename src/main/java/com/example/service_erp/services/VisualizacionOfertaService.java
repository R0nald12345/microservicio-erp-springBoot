package com.example.service_erp.services;

import com.example.service_erp.entities.OfertaTrabajo;
import com.example.service_erp.entities.VisualizacionOferta;
import com.example.service_erp.repositories.OfertaTrabajoRepository;
import com.example.service_erp.repositories.VisualizacionOfertaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VisualizacionOfertaService {

    private final VisualizacionOfertaRepository repository;
    private final OfertaTrabajoRepository ofertaRepository;

    public VisualizacionOfertaService(VisualizacionOfertaRepository repository, OfertaTrabajoRepository ofertaRepository) {
        this.repository = repository;
        this.ofertaRepository = ofertaRepository;
    }

    public List<VisualizacionOferta> obtenerTodas() {
        return repository.findAll();
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

    public void eliminar(UUID id) {
        repository.deleteById(id);
    }
}
