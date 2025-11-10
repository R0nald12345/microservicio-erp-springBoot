package com.example.service_erp.resolvers;

import com.example.service_erp.entities.Empresa;
import com.example.service_erp.entities.OfertaTrabajo;
import com.example.service_erp.entities.Postulacion;
import com.example.service_erp.entities.VisualizacionOferta;
import com.example.service_erp.services.OfertaTrabajoService;
import com.example.service_erp.services.PostulacionService;
import com.example.service_erp.services.VisualizacionOfertaService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Controller
public class OfertaTrabajoResolver {

    private final OfertaTrabajoService service;
    private final PostulacionService postulacionService;
    private final VisualizacionOfertaService visualizacionOfertaService;

    public OfertaTrabajoResolver(OfertaTrabajoService service, PostulacionService postulacionService, VisualizacionOfertaService visualizacionOfertaService) {
        this.service = service;
        this.postulacionService = postulacionService;
        this.visualizacionOfertaService = visualizacionOfertaService;
    }

    @SchemaMapping(typeName = "OfertaTrabajo", field = "empresa")
    @Transactional(readOnly = true)
    public Empresa empresa(OfertaTrabajo oferta) {
        // Acceso directo al campo para evitar problemas con Lombok en el IDE
        return oferta.empresa;
    }

    @SchemaMapping(typeName = "OfertaTrabajo", field = "postulaciones")
    @Transactional(readOnly = true)
    public List<Postulacion> postulaciones(OfertaTrabajo oferta, @Argument Integer limit) {
        // Usar servicio optimizado con paginación
        return postulacionService.obtenerPorOfertaId(oferta.getId(), limit != null ? limit : 10);
    }

    @SchemaMapping(typeName = "OfertaTrabajo", field = "visualizaciones")
    @Transactional(readOnly = true)
    public List<VisualizacionOferta> visualizaciones(OfertaTrabajo oferta, @Argument Integer limit) {
        // Usar servicio optimizado con paginación
        return visualizacionOfertaService.obtenerPorOfertaId(oferta.getId(), limit != null ? limit : 10);
    }

    @QueryMapping
    public List<OfertaTrabajo> obtenerOfertasTrabajo(@Argument Integer limit) {
        return service.obtenerTodas(limit);
    }

    @QueryMapping
    public OfertaTrabajo obtenerOfertaTrabajoPorId(@Argument UUID id) {
        return service.obtenerPorId(id);
    }

    @QueryMapping
    public List<OfertaTrabajo> obtenerOfertasPorEmpresa(@Argument UUID empresaId, @Argument Integer limit) {
        return service.obtenerPorEmpresaId(empresaId, limit);
    }

    @MutationMapping
    public OfertaTrabajo crearOfertaTrabajo(
            @Argument String titulo,
            @Argument String descripcion,
            @Argument Double salario,
            @Argument String ubicacion,
            @Argument String requisitos,
            @Argument String fechaPublicacion,
            @Argument UUID empresaId
    ) {
        return service.crear(titulo, descripcion, salario, ubicacion, requisitos, fechaPublicacion, empresaId);
    }

    @MutationMapping
    public String eliminarOfertaTrabajo(@Argument UUID id) {
        service.eliminar(id);
        return "Oferta de trabajo eliminada correctamente";
    }
}
