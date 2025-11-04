package com.example.service_erp.services;

import com.example.service_erp.dto.EmpresaFeaturesDTO;
import com.example.service_erp.dto.OfertaFeaturesDTO;
import com.example.service_erp.dto.PostulanteFeaturesDTO;
import com.example.service_erp.entities.OfertaTrabajo;
import com.example.service_erp.entities.Postulacion;
import com.example.service_erp.repositories.EmpresaRepository;
import com.example.service_erp.repositories.OfertaTrabajoRepository;
import com.example.service_erp.repositories.PostulacionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostulacionService {

    private final PostulacionRepository repository;
    private final OfertaTrabajoRepository ofertaRepository;
    private final EmpresaRepository empresaRepository;

    public PostulacionService(PostulacionRepository repository, OfertaTrabajoRepository ofertaRepository, EmpresaRepository empresaRepository) {
        this.repository = repository;
        this.ofertaRepository = ofertaRepository;
        this.empresaRepository = empresaRepository;
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

    // FEATURES DEL POSTULANTE
    public List<PostulanteFeaturesDTO> obtenerFeaturesPostulantes() {
        List<Postulacion> postulaciones = repository.findAll();
        return postulaciones.stream()
                .map(p -> PostulanteFeaturesDTO.builder()
                        .idPostulante(p.getId())
                        .aniosExperiencia(p.getAniosExperiencia())
                        .nivelEducacion(p.getNivelEducacion())
                        .habilidades(p.getHabilidades())
                        .idiomas(p.getIdiomas())
                        .certificaciones(p.getCertificaciones())
                        .puestoActual(p.getPuestoActual())
                        .idOfertaTrabajo(p.getOferta().getId())
                        .build())
                .collect(Collectors.toList());
    }

    // FEATURES DE LA OFERTA
    public List<OfertaFeaturesDTO> obtenerFeaturesOfertas() {
        List<OfertaTrabajo> ofertas = ofertaRepository.findAll();
        return ofertas.stream()
                .map(ot -> OfertaFeaturesDTO.builder()
                        .idOferta(ot.getId())
                        .titulo(ot.getTitulo())
                        .salario(ot.getSalario())
                        .ubicacion(ot.getUbicacion())
                        .requisitos(ot.getRequisitos())
                        .idEmpresa(ot.getEmpresa().getId())
                        .build())
                .collect(Collectors.toList());
    }

    // FEATURES DE LA EMPRESA
    public List<EmpresaFeaturesDTO> obtenerFeaturesEmpresas() {
        return empresaRepository.findAll().stream()
                .map(emp -> EmpresaFeaturesDTO.builder()
                        .idEmpresa(emp.getId())
                        .rubro(emp.getRubro())
                        .build())
                .collect(Collectors.toList());
    }
}
