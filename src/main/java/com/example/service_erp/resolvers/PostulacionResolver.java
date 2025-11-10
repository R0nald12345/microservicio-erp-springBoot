package com.example.service_erp.resolvers;

import java.util.List;
import java.util.UUID;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.example.service_erp.entities.Entrevista;
import com.example.service_erp.entities.OfertaTrabajo;
import com.example.service_erp.entities.Postulacion;
import com.example.service_erp.services.EntrevistaService;
import com.example.service_erp.services.OfertaTrabajoService;
import com.example.service_erp.services.PostulacionService;

@Controller
public class PostulacionResolver {

    private final PostulacionService service;
    private final OfertaTrabajoService ofertaTrabajoService;
    private final EntrevistaService entrevistaService;

    public PostulacionResolver(PostulacionService service, OfertaTrabajoService ofertaTrabajoService,
                              EntrevistaService entrevistaService) {
        this.service = service;
        this.ofertaTrabajoService = ofertaTrabajoService;
        this.entrevistaService = entrevistaService;
    }

    @SchemaMapping(typeName = "Postulacion", field = "oferta")
    @Transactional(readOnly = true)
    public OfertaTrabajo oferta(Postulacion postulacion) {
        return ofertaTrabajoService.obtenerPorId(postulacion.getOferta().getId());
    }

    @SchemaMapping(typeName = "Postulacion", field = "entrevistas")
    @Transactional(readOnly = true)
    public List<Entrevista> entrevistas(Postulacion postulacion, @Argument Integer limit) {
        return entrevistaService.obtenerPorPostulacionId(postulacion.getId(), limit != null ? limit : 10);
    }

    @QueryMapping
    @Transactional(readOnly = true)
    public List<Postulacion> obtenerPostulaciones(@Argument Integer limit) {
        return service.obtenerTodas(limit);
    }

    @QueryMapping
    @Transactional(readOnly = true)
    public Postulacion obtenerPostulacionPorId(@Argument UUID id) {
        return service.obtenerPorId(id);
    }

    @MutationMapping
    @Transactional
    public Postulacion crearPostulacion(
            @Argument String nombre,
            @Argument Integer aniosExperiencia,
            @Argument String nivelEducacion,
            @Argument String habilidades,
            @Argument String idiomas,
            @Argument String certificaciones,
            @Argument String puestoActual,
            @Argument String urlCv,
            @Argument String fechaPostulacion,
            @Argument String estado,
            @Argument String telefono,
            @Argument String email,
            @Argument UUID ofertaId
    ) {
        return service.crear(nombre, aniosExperiencia, nivelEducacion, habilidades, idiomas, 
                           certificaciones, puestoActual, urlCv, fechaPostulacion, estado, telefono, email, ofertaId);
    }

    @MutationMapping
    @Transactional
    public Postulacion actualizarPostulacion(
            @Argument UUID id,
            @Argument String nombre,
            @Argument Integer aniosExperiencia,
            @Argument String nivelEducacion,
            @Argument String habilidades,
            @Argument String idiomas,
            @Argument String certificaciones,
            @Argument String puestoActual,
            @Argument String urlCv,
            @Argument String fechaPostulacion,
            @Argument String estado,
            @Argument String telefono,
            @Argument String email
    ) {
        return service.actualizar(id, nombre, aniosExperiencia, nivelEducacion, habilidades, idiomas,
                                certificaciones, puestoActual, urlCv, fechaPostulacion, estado, telefono, email);
    }

    @MutationMapping
    @Transactional
    public String eliminarPostulacion(@Argument UUID id) {
        service.eliminar(id);
        return "Postulación eliminada correctamente";
    }
}