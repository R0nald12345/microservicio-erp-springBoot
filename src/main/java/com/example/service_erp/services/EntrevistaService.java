package com.example.service_erp.services;

import com.example.service_erp.entities.Entrevista;
import com.example.service_erp.entities.Postulacion;
import com.example.service_erp.repositories.EntrevistaRepository;
import com.example.service_erp.repositories.PostulacionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EntrevistaService {

    private final EntrevistaRepository repository;
    private final PostulacionRepository postulacionRepository;
    private static final int DEFAULT_LIMIT = 10;

    public EntrevistaService(EntrevistaRepository repository, PostulacionRepository postulacionRepository) {
        this.repository = repository;
        this.postulacionRepository = postulacionRepository;
    }

    public List<Entrevista> obtenerTodas() {
        return obtenerTodas(DEFAULT_LIMIT);
    }

    public List<Entrevista> obtenerTodas(Integer limit) {
        int pageSize = (limit != null && limit > 0) ? Math.min(limit, 100) : DEFAULT_LIMIT;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Entrevista> page = repository.findAllOrderedByCreatedAtDesc(pageable);
        return page.getContent();
    }

    public List<Entrevista> obtenerPorPostulacionId(UUID postulacionId, Integer limit) {
        int pageSize = (limit != null && limit > 0) ? Math.min(limit, 100) : DEFAULT_LIMIT;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Entrevista> page = repository.findByPostulacionIdOrderedByCreatedAtDesc(postulacionId, pageable);
        return page.getContent();
    }

    public Entrevista obtenerPorId(UUID id) {
        return repository.findById(id).orElse(null);
    }

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

        return repository.save(entrevista);
    }

    public void eliminar(UUID id) {
        repository.deleteById(id);
    }
}
