package com.example.service_erp.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.service_erp.entities.Entrevista;
import com.example.service_erp.entities.Postulacion;
import com.example.service_erp.repositories.EntrevistaRepository;
import com.example.service_erp.repositories.PostulacionRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EntrevistaService {

    private final EntrevistaRepository repository;
    private final PostulacionRepository postulacionRepository;
    private static final int DEFAULT_LIMIT = 10;

    public EntrevistaService(EntrevistaRepository repository, PostulacionRepository postulacionRepository) {
        this.repository = repository;
        this.postulacionRepository = postulacionRepository;
    }

    @Transactional(readOnly = true)
    public List<Entrevista> obtenerTodas() {
        return obtenerTodas(DEFAULT_LIMIT);
    }

    @Transactional(readOnly = true)
    public List<Entrevista> obtenerTodas(Integer limit) {
        int pageSize = (limit != null && limit > 0) ? Math.min(limit, 100) : DEFAULT_LIMIT;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Entrevista> page = repository.findAllOrderedByCreatedAtDesc(pageable);
        return page.getContent();
    }

    @Transactional(readOnly = true)
    public List<Entrevista> obtenerPorPostulacionId(UUID postulacionId, Integer limit) {
        int pageSize = (limit != null && limit > 0) ? Math.min(limit, 100) : DEFAULT_LIMIT;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Entrevista> page = repository.findByPostulacionIdOrderedByCreatedAtDesc(postulacionId, pageable);
        return page.getContent();
    }

    @Transactional(readOnly = true)
    public Entrevista obtenerPorId(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public Entrevista crear(String fecha, Integer duracionMin, String objetivosTotales,
                           String objetivosCubiertos, String entrevistador, UUID postulacionId) {
        Postulacion postulacion = postulacionRepository.findById(postulacionId)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));

        Entrevista entrevista = Entrevista.builder()
                .fecha(fecha)
                .duracionMin(duracionMin)
                .objetivosTotales(objetivosTotales)
                .objetivosCubiertos(objetivosCubiertos)
                .entrevistador(entrevistador)
                .postulacion(postulacion)
                .build();

        Entrevista entrevistaGuardada = repository.save(entrevista);
        log.info("Entrevista creada exitosamente - ID: {}", entrevistaGuardada.getId());
        return entrevistaGuardada;
    }

    @Transactional
    public Entrevista actualizar(UUID id, String fecha, Integer duracionMin, String objetivosTotales,
                                String objetivosCubiertos, String entrevistador) {
        Entrevista entrevista = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrevista no encontrada"));

        if (fecha != null && !fecha.isEmpty()) {
            entrevista.setFecha(fecha);
        }
        if (duracionMin != null) {
            entrevista.setDuracionMin(duracionMin);
        }
        if (objetivosTotales != null && !objetivosTotales.isEmpty()) {
            entrevista.setObjetivosTotales(objetivosTotales);
        }
        if (objetivosCubiertos != null && !objetivosCubiertos.isEmpty()) {
            entrevista.setObjetivosCubiertos(objetivosCubiertos);
        }
        if (entrevistador != null && !entrevistador.isEmpty()) {
            entrevista.setEntrevistador(entrevistador);
        }

        return repository.save(entrevista);
    }

    @Transactional
    public void eliminar(UUID id) {
        repository.deleteById(id);
    }
}