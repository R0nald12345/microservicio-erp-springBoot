package com.example.service_erp.resolvers;

import com.example.service_erp.entities.Empresa;
import com.example.service_erp.entities.OfertaTrabajo;
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
public class OfertaTrabajoResolver {

    private final OfertaTrabajoService service;

    public OfertaTrabajoResolver(OfertaTrabajoService service) {
        this.service = service;
    }

    @SchemaMapping(typeName = "OfertaTrabajo", field = "empresa")
    @Transactional(readOnly = true)
    public Empresa empresa(OfertaTrabajo oferta) {
        // Acceso directo al campo para evitar problemas con Lombok en el IDE
        return oferta.empresa;
    }

    @QueryMapping
    public List<OfertaTrabajo> obtenerOfertasTrabajo() {
        return service.obtenerTodas();
    }

    @QueryMapping
    public OfertaTrabajo obtenerOfertaTrabajoPorId(@Argument UUID id) {
        return service.obtenerPorId(id);
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
