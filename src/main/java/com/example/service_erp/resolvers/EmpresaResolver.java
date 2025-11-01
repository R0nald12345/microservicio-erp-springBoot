package com.example.service_erp.resolvers;

import com.example.service_erp.entities.Empresa;
import com.example.service_erp.services.EmpresaService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
public class EmpresaResolver {

    private final EmpresaService service;

    public EmpresaResolver(EmpresaService service) {
        this.service = service;
    }

    @QueryMapping
    public List<Empresa> obtenerEmpresas() {
        return service.obtenerTodas();
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
    public String eliminarEmpresa(@Argument UUID id) {
        service.eliminar(id);
        return "Empresa eliminada correctamente";
    }
}
