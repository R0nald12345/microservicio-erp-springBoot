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
import com.example.service_erp.services.EmpresaService;
import com.example.service_erp.services.OfertaTrabajoService;

@Controller
public class EmpresaResolver {

    private final EmpresaService service;
    private final OfertaTrabajoService ofertaTrabajoService;

    public EmpresaResolver(EmpresaService service, OfertaTrabajoService ofertaTrabajoService) {
        this.service = service;
        this.ofertaTrabajoService = ofertaTrabajoService;
    }

    @SchemaMapping(typeName = "Empresa", field = "ofertas")
    @Transactional(readOnly = true)
    public List<OfertaTrabajo> ofertas(Empresa empresa, @Argument Integer limit) {
        // Usar servicio optimizado con paginación en lugar de cargar todas las relaciones
        return ofertaTrabajoService.obtenerPorEmpresaId(empresa.getId(), limit != null ? limit : 10);
    }

    @QueryMapping
    public List<Empresa> obtenerEmpresas(@Argument Integer limit) {
        return service.obtenerTodas(limit);
    }

    @QueryMapping
    public Empresa obtenerEmpresaPorId(@Argument UUID id) {
        return service.obtenerPorId(id);
    }

 

        @MutationMapping
    public Empresa crearEmpresa(
            @Argument String nombre,
            @Argument String correo,
            @Argument String rubro
    ) {
        return service.crear(nombre, correo, rubro);
    }

    @MutationMapping
    public Empresa actualizarEmpresa(
            @Argument UUID id,
            @Argument String nombre,
            @Argument String correo,
            @Argument String rubro
    ) {
        return service.actualizar(id, nombre, correo, rubro);
    }

    @MutationMapping
    public String eliminarEmpresa(@Argument UUID id) {
        service.eliminar(id);
        return "Empresa eliminada correctamente";
    }
}

   

