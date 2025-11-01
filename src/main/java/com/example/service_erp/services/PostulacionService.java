package com.example.service_erp.services;

import com.example.service_erp.entities.OfertaTrabajo;
import com.example.service_erp.entities.Postulacion;
import com.example.service_erp.repositories.OfertaTrabajoRepository;
import com.example.service_erp.repositories.PostulacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PostulacionService {

    private final PostulacionRepository repository;
    private final OfertaTrabajoRepository ofertaRepository;

    public PostulacionService(PostulacionRepository repository, OfertaTrabajoRepository ofertaRepository) {
        this.repository = repository;
        this.ofertaRepository = ofertaRepository;
    }

    public List<Postulacion> obtenerTodas() {
        return repository.findAll();
    }

    public Postulacion obtenerPorId(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Postulacion crear(String nombre, int aniosExperiencia, String nivelEducacion,
                             String habilidades, String idiomas, String certificaciones,
                             String puestoActual, String urlCv, String fechaPostulacion,
                             String estado, UUID ofertaId) {

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
                .oferta(oferta)
                .build();

        return repository.save(postulacion);
    }

    public void eliminar(UUID id) {
        repository.deleteById(id);
    }
}
