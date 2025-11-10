package com.example.service_erp.services;

import com.example.service_erp.entities.Evaluacion;
import com.example.service_erp.entities.Entrevista;
import com.example.service_erp.repositories.EvaluacionRepository;
import com.example.service_erp.repositories.EntrevistaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EvaluacionService {

    private final EvaluacionRepository repository;
    private final EntrevistaRepository entrevistaRepository;
    private static final int DEFAULT_LIMIT = 10;

    public EvaluacionService(EvaluacionRepository repository, EntrevistaRepository entrevistaRepository) {
        this.repository = repository;
        this.entrevistaRepository = entrevistaRepository;
    }

    public List<Evaluacion> obtenerTodas() {
        return obtenerTodas(DEFAULT_LIMIT);
    }

    public List<Evaluacion> obtenerTodas(Integer limit) {
        int pageSize = (limit != null && limit > 0) ? Math.min(limit, 100) : DEFAULT_LIMIT;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Evaluacion> page = repository.findAllOrderedByCreatedAtDesc(pageable);
        return page.getContent();
    }

    public List<Evaluacion> obtenerPorEntrevistaId(UUID entrevistaId, Integer limit) {
        int pageSize = (limit != null && limit > 0) ? Math.min(limit, 100) : DEFAULT_LIMIT;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Evaluacion> page = repository.findByEntrevistaIdOrderedByCreatedAtDesc(entrevistaId, pageable);
        return page.getContent();
    }

    public Evaluacion obtenerPorId(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Evaluacion crear(Double tecnica, Double actitud, Double general,
                            String comentarios, UUID entrevistaId) {

        Entrevista entrevista = entrevistaRepository.findById(entrevistaId)
                .orElseThrow(() -> new RuntimeException("Entrevista no encontrada"));

        Evaluacion evaluacion = Evaluacion.builder()
                .calificacionTecnica(tecnica)
                .calificacionActitud(actitud)
                .calificacionGeneral(general)
                .comentarios(comentarios)
                .entrevista(entrevista)
                .build();

        return repository.save(evaluacion);
    }

    public void eliminar(UUID id) {
        repository.deleteById(id);
    }
}
