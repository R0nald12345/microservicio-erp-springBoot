package com.example.service_erp.resolvers;

import com.example.service_erp.entities.Entrevista;
import com.example.service_erp.services.EntrevistaService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class EntrevistaResolver {

    private final EntrevistaService service;

    public EntrevistaResolver(EntrevistaService service) {
        this.service = service;
    }

    @QueryMapping
    public List<Entrevista> obtenerEntrevistas() {
        return service.obtenerTodas();
    }

    @QueryMapping
    public Entrevista obtenerEntrevistaPorId(@Argument UUID id) {
        return service.obtenerPorId(id);
    }

    @MutationMapping
    public Entrevista crearEntrevista(
            @Argument String fecha,
            @Argument Integer duracionMin,
            @Argument String objetivosTotales,
            @Argument String objetivosCubiertos,
            @Argument String entrevistador,
            @Argument UUID postulacionId
    ) {
        return service.crear(fecha, duracionMin, objetivosTotales, objetivosCubiertos, entrevistador, postulacionId);
    }

    @MutationMapping
    public String eliminarEntrevista(@Argument UUID id) {
        service.eliminar(id);
        return "Entrevista eliminada correctamente";
    }
}
