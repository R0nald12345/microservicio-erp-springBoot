package com.example.service_erp.resolvers;

import com.example.service_erp.entities.Entrevista;
import com.example.service_erp.entities.Evaluacion;
import com.example.service_erp.entities.Postulacion;
import com.example.service_erp.services.EntrevistaService;
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
public class EntrevistaResolver {

    private final EntrevistaService service;
    private final EvaluacionService evaluacionService;

    public EntrevistaResolver(EntrevistaService service, EvaluacionService evaluacionService) {
        this.service = service;
        this.evaluacionService = evaluacionService;
    }

    @SchemaMapping(typeName = "Entrevista", field = "postulacion")
    @Transactional(readOnly = true)
    public Postulacion postulacion(Entrevista entrevista) {
        // Acceso directo al campo para evitar problemas con Lombok en el IDE
        return entrevista.postulacion;
    }

    @SchemaMapping(typeName = "Entrevista", field = "evaluaciones")
    @Transactional(readOnly = true)
    public List<Evaluacion> evaluaciones(Entrevista entrevista, @Argument Integer limit) {
        // Usar servicio optimizado con paginación
        return evaluacionService.obtenerPorEntrevistaId(entrevista.getId(), limit != null ? limit : 10);
    }

    @QueryMapping
    public List<Entrevista> obtenerEntrevistas(@Argument Integer limit) {
        return service.obtenerTodas(limit);
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
