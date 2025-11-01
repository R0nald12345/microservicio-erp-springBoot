package com.example.service_erp.resolvers;

import com.example.service_erp.entities.Postulacion;
import com.example.service_erp.services.PostulacionService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class PostulacionResolver {

    private final PostulacionService service;

    public PostulacionResolver(PostulacionService service) {
        this.service = service;
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
            @Argument UUID ofertaId
    ) {
        return service.crear(nombre, aniosExperiencia, nivelEducacion, habilidades, idiomas,
                certificaciones, puestoActual, urlCv, fechaPostulacion, estado, ofertaId);
    }

    @MutationMapping
    public String eliminarPostulacion(@Argument UUID id) {
        service.eliminar(id);
        return "Postulación eliminada correctamente";
    }
}
