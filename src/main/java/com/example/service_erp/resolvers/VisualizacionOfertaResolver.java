package com.example.service_erp.resolvers;

import com.example.service_erp.entities.VisualizacionOferta;
import com.example.service_erp.services.VisualizacionOfertaService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class VisualizacionOfertaResolver {

    private final VisualizacionOfertaService service;

    public VisualizacionOfertaResolver(VisualizacionOfertaService service) {
        this.service = service;
    }

    @QueryMapping
    public List<VisualizacionOferta> obtenerVisualizacionesOferta() {
        return service.obtenerTodas();
    }

    @QueryMapping
    public VisualizacionOferta obtenerVisualizacionOfertaPorId(@Argument UUID id) {
        return service.obtenerPorId(id);
    }

    @MutationMapping
    public VisualizacionOferta crearVisualizacionOferta(
            @Argument String fechaVisualizacion,
            @Argument String origen,
            @Argument UUID ofertaId
    ) {
        return service.crear(fechaVisualizacion, origen, ofertaId);
    }

    @MutationMapping
    public String eliminarVisualizacionOferta(@Argument UUID id) {
        service.eliminar(id);
        return "Visualización eliminada correctamente";
    }
}
