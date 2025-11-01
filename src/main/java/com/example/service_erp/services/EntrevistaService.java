package com.example.service_erp.services;

import com.example.service_erp.entities.Entrevista;
import com.example.service_erp.entities.Postulacion;
import com.example.service_erp.repositories.EntrevistaRepository;
import com.example.service_erp.repositories.PostulacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EntrevistaService {

    private final EntrevistaRepository repository;
    private final PostulacionRepository postulacionRepository;

    public EntrevistaService(EntrevistaRepository repository, PostulacionRepository postulacionRepository) {
        this.repository = repository;
        this.postulacionRepository = postulacionRepository;
    }

    public List<Entrevista> obtenerTodas() {
        return repository.findAll();
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
