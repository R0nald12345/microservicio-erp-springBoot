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
import com.example.service_erp.entities.Evaluacion;
import com.example.service_erp.services.EntrevistaService;
import com.example.service_erp.services.EvaluacionService;

@Controller
public class EvaluacionResolver {

    private final EvaluacionService service;
    private final EntrevistaService entrevistaService;

    public EvaluacionResolver(EvaluacionService service, EntrevistaService entrevistaService) {
        this.service = service;
        this.entrevistaService = entrevistaService;
    }

    @SchemaMapping(typeName = "Evaluacion", field = "entrevista")
    @Transactional(readOnly = true)
    public Entrevista entrevista(Evaluacion evaluacion) {
        return entrevistaService.obtenerPorId(evaluacion.getEntrevista().getId());
    }

    @QueryMapping
    @Transactional(readOnly = true)
    public List<Evaluacion> obtenerEvaluaciones(@Argument Integer limit) {
        return service.obtenerTodas(limit);
    }

    @QueryMapping
    @Transactional(readOnly = true)
    public Evaluacion obtenerEvaluacionPorId(@Argument UUID id) {
        return service.obtenerPorId(id);
    }

    @MutationMapping
    @Transactional
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
    @Transactional
    public Evaluacion actualizarEvaluacion(
            @Argument UUID id,
            @Argument Double calificacionTecnica,
            @Argument Double calificacionActitud,
            @Argument Double calificacionGeneral,
            @Argument String comentarios
    ) {
        return service.actualizar(id, calificacionTecnica, calificacionActitud, calificacionGeneral, comentarios);
    }

    @MutationMapping
    @Transactional
    public String eliminarEvaluacion(@Argument UUID id) {
        service.eliminar(id);
        return "Evaluación eliminada correctamente";
    }
}