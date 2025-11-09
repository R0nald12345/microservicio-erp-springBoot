package com.example.service_erp.resolvers;

import com.example.service_erp.entities.Entrevista;
import com.example.service_erp.entities.OfertaTrabajo;
import com.example.service_erp.entities.Postulacion;
import com.example.service_erp.services.PostulacionService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Controller
public class PostulacionResolver {

    private final PostulacionService service;

    public PostulacionResolver(PostulacionService service) {
        this.service = service;
    }

    @SchemaMapping(typeName = "Postulacion", field = "oferta")
    @Transactional(readOnly = true)
    public OfertaTrabajo oferta(Postulacion postulacion) {
        // Acceso directo al campo para evitar problemas con Lombok en el IDE
        return postulacion.oferta;
    }

    @SchemaMapping(typeName = "Postulacion", field = "entrevistas")
    @Transactional(readOnly = true)
    public List<Entrevista> entrevistas(Postulacion postulacion) {
        // Acceso directo al campo para evitar problemas con Lombok en el IDE
        return postulacion.entrevistas;
    }

    @QueryMapping
    public List<Postulacion> obtenerPostulaciones() {
        return service.obtenerTodas();
    }

    @QueryMapping
    public Postulacion obtenerPostulacionPorId(@Argument UUID id) {
        return service.obtenerPorId(id);
    }

    @MutationMapping
    public Postulacion crearPostulacion(
            @Argument String nombre,
            @Argument int aniosExperiencia,
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
    public String eliminarPostulacion(@Argument UUID id) {
        service.eliminar(id);
        return "Postulación eliminada correctamente";
    }
}
