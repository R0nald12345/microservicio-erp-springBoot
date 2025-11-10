package com.example.service_erp.resolvers;

import java.util.List;
import java.util.UUID;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.example.service_erp.entities.Empresa;
import com.example.service_erp.entities.OfertaTrabajo;
import com.example.service_erp.entities.Postulacion;
import com.example.service_erp.entities.VisualizacionOferta;
import com.example.service_erp.services.EmpresaService;
import com.example.service_erp.services.OfertaTrabajoService;
import com.example.service_erp.services.PostulacionService;
import com.example.service_erp.services.VisualizacionOfertaService;

@Controller
public class OfertaTrabajoResolver {

    private final OfertaTrabajoService service;
    private final EmpresaService empresaService;
    private final PostulacionService postulacionService;
    private final VisualizacionOfertaService visualizacionService;

    public OfertaTrabajoResolver(OfertaTrabajoService service, EmpresaService empresaService,
                                 PostulacionService postulacionService, VisualizacionOfertaService visualizacionService) {
        this.service = service;
        this.empresaService = empresaService;
        this.postulacionService = postulacionService;
        this.visualizacionService = visualizacionService;
    }

    @SchemaMapping(typeName = "OfertaTrabajo", field = "empresa")
    @Transactional(readOnly = true)
    public Empresa empresa(OfertaTrabajo oferta) {
        return empresaService.obtenerPorId(oferta.getEmpresa().getId());
    }

    @SchemaMapping(typeName = "OfertaTrabajo", field = "postulaciones")
    @Transactional(readOnly = true)
    public List<Postulacion> postulaciones(OfertaTrabajo oferta, @Argument Integer limit) {
        return postulacionService.obtenerPorOfertaId(oferta.getId(), limit != null ? limit : 10);
    }

    @SchemaMapping(typeName = "OfertaTrabajo", field = "visualizaciones")
    @Transactional(readOnly = true)
    public List<VisualizacionOferta> visualizaciones(OfertaTrabajo oferta, @Argument Integer limit) {
        return visualizacionService.obtenerPorOfertaId(oferta.getId(), limit != null ? limit : 10);
    }

    @QueryMapping
    @Transactional(readOnly = true)
    public List<OfertaTrabajo> obtenerOfertasTrabajo(@Argument Integer limit) {
        return service.obtenerTodas(limit);
    }

    @QueryMapping
    @Transactional(readOnly = true)
    public OfertaTrabajo obtenerOfertaTrabajoPorId(@Argument UUID id) {
        return service.obtenerPorId(id);
    }

    @QueryMapping
    public List<OfertaTrabajo> obtenerOfertasPorEmpresa(@Argument UUID empresaId, @Argument Integer limit) {
        return service.obtenerPorEmpresaId(empresaId, limit);
    }

    @MutationMapping
    @Transactional
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
    @Transactional
    public OfertaTrabajo actualizarOfertaTrabajo(
            @Argument UUID id,
            @Argument String titulo,
            @Argument String descripcion,
            @Argument Double salario,
            @Argument String ubicacion,
            @Argument String requisitos,
            @Argument String fechaPublicacion
    ) {
        return service.actualizar(id, titulo, descripcion, salario, ubicacion, requisitos, fechaPublicacion);
    }

    @MutationMapping
    @Transactional
    public String eliminarOfertaTrabajo(@Argument UUID id) {
        service.eliminar(id);
        return "Oferta de trabajo eliminada correctamente";
    }
}