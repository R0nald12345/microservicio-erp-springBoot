package com.example.service_erp.services;

import com.example.service_erp.entities.Empresa;
import com.example.service_erp.repositories.EmpresaRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class EmpresaService {

    private final EmpresaRepository repository;

    public EmpresaService(EmpresaRepository repository) {
        this.repository = repository;
    }

    public List<Empresa> obtenerTodas() {
        return repository.findAll();
    }

    public Empresa obtenerPorId(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Empresa crear(String nombre, String correo, String rubro) {
        Empresa empresa = Empresa.builder()
                .nombre(nombre)
                .correo(correo)
                .rubro(rubro)
                .build();
        return repository.save(empresa);
    }

    public void eliminar(UUID id) {
        repository.deleteById(id);
    }
}
