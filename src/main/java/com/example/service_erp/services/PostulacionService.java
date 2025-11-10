package com.example.service_erp.services;

import com.example.service_erp.entities.OfertaTrabajo;
import com.example.service_erp.entities.Postulacion;
import com.example.service_erp.repositories.OfertaTrabajoRepository;
import com.example.service_erp.repositories.PostulacionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostulacionService {

    private final PostulacionRepository repository;
    private final OfertaTrabajoRepository ofertaRepository;
    private static final int DEFAULT_LIMIT = 10;

    public PostulacionService(PostulacionRepository repository, OfertaTrabajoRepository ofertaRepository) {
        this.repository = repository;
        this.ofertaRepository = ofertaRepository;
    }

    public List<Postulacion> obtenerTodas() {
        return obtenerTodas(DEFAULT_LIMIT);
    }

    public List<Postulacion> obtenerTodas(Integer limit) {
        int pageSize = (limit != null && limit > 0) ? Math.min(limit, 100) : DEFAULT_LIMIT;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Postulacion> page = repository.findAllOrderedByCreatedAtDesc(pageable);
        return page.getContent();
    }

    public List<Postulacion> obtenerPorOfertaId(UUID ofertaId, Integer limit) {
        int pageSize = (limit != null && limit > 0) ? Math.min(limit, 100) : DEFAULT_LIMIT;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Postulacion> page = repository.findByOfertaIdOrderedByCreatedAtDesc(ofertaId, pageable);
        return page.getContent();
    }

    public Postulacion obtenerPorId(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Postulacion crear(String nombre, int aniosExperiencia, String nivelEducacion,
                             String habilidades, String idiomas, String certificaciones,
                             String puestoActual, String urlCv, String fechaPostulacion,
                             String estado, String telefono, String email, UUID ofertaId) {

        OfertaTrabajo oferta = ofertaRepository.findById(ofertaId)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));

        Postulacion postulacion = Postulacion.builder()
                .nombre(nombre)
                .aniosExperiencia(aniosExperiencia)
                .nivelEducacion(nivelEducacion)
                .habilidades(habilidades)
                .idiomas(idiomas)
                .certificaciones(certificaciones)
                .puestoActual(puestoActual)
                .urlCv(urlCv)
                .fechaPostulacion(fechaPostulacion)
                .estado(estado)
                .telefono(telefono)
                .email(email)
                .oferta(oferta)
                .build();

        return repository.save(postulacion);
    }

    public void eliminar(UUID id) {
        repository.deleteById(id);
    }
}
