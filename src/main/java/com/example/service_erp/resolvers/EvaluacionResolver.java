package com.example.service_erp.resolvers;

import com.example.service_erp.entities.Entrevista;
import com.example.service_erp.entities.Evaluacion;
import com.example.service_erp.services.EvaluacionService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Controller
public class EvaluacionResolver {

    private final EvaluacionService service;

    public EvaluacionResolver(EvaluacionService service) {
        this.service = service;
    }

    @SchemaMapping(typeName = "Evaluacion", field = "entrevista")
    @Transactional(readOnly = true)
    public Entrevista entrevista(Evaluacion evaluacion) {
        // Acceso directo al campo para evitar problemas con Lombok en el IDE
        return evaluacion.entrevista;
    }

    @QueryMapping
    public List<Evaluacion> obtenerEvaluaciones() {
        return service.obtenerTodas();
    }

    @QueryMapping
    public Evaluacion obtenerEvaluacionPorId(@Argument UUID id) {
        return service.obtenerPorId(id);
    }

    @MutationMapping
    public Evaluacion crearEvaluacion(
            @Argument Double calificacionTecnica,
            @Argument Double calificacionActitud,
            @Argument Double calificacionGeneral,
            @Argument String comentarios,
            @Argument UUID entrevistaId
    ) {
        return service.crear(calificacionTecnica, calificacionActitud, calificacionGeneral, comentarios, entrevistaId);
    }

    @MutationMapping
    public String eliminarEvaluacion(@Argument UUID id) {
        service.eliminar(id);
        return "Evaluación eliminada correctamente";
    }
}
