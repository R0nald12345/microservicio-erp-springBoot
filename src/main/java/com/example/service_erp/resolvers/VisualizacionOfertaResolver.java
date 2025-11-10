package com.example.service_erp.resolvers;

import com.example.service_erp.entities.OfertaTrabajo;
import com.example.service_erp.entities.VisualizacionOferta;
import com.example.service_erp.services.VisualizacionOfertaService;
import com.example.service_erp.services.OfertaTrabajoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Controller
public class VisualizacionOfertaResolver {

    private final VisualizacionOfertaService service;
    private final OfertaTrabajoService ofertaTrabajoService;

    public VisualizacionOfertaResolver(VisualizacionOfertaService service, OfertaTrabajoService ofertaTrabajoService) {
        this.service = service;
        this.ofertaTrabajoService = ofertaTrabajoService;
    }

    @SchemaMapping(typeName = "VisualizacionOferta", field = "oferta")
    @Transactional(readOnly = true)
    public OfertaTrabajo oferta(VisualizacionOferta visualizacion) {
        return ofertaTrabajoService.obtenerPorId(visualizacion.getOferta().getId());
    }

    @QueryMapping
    @Transactional(readOnly = true)
    public List<VisualizacionOferta> obtenerVisualizacionesOferta(@Argument Integer limit) {
        return service.obtenerTodas(limit);
    }

    @QueryMapping
    @Transactional(readOnly = true)
    public VisualizacionOferta obtenerVisualizacionOfertaPorId(@Argument UUID id) {
        return service.obtenerPorId(id);
    }

    @MutationMapping
    @Transactional
    public VisualizacionOferta crearVisualizacionOferta(
            @Argument String fechaVisualizacion,
            @Argument String origen,
            @Argument UUID ofertaId
    ) {
        return service.crear(fechaVisualizacion, origen, ofertaId);
    }

    @MutationMapping
    @Transactional
    public VisualizacionOferta actualizarVisualizacionOferta(
            @Argument UUID id,
            @Argument String fechaVisualizacion,
            @Argument String origen
    ) {
        return service.actualizar(id, fechaVisualizacion, origen);
    }

    @MutationMapping
    @Transactional
    public String eliminarVisualizacionOferta(@Argument UUID id) {
        service.eliminar(id);
        return "Visualización eliminada correctamente";
    }
}