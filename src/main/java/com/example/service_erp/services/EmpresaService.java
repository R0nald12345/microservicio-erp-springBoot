package com.example.service_erp.services;

import com.example.service_erp.entities.Empresa;
import com.example.service_erp.repositories.EmpresaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class EmpresaService {

    private final EmpresaRepository repository;
    private static final int DEFAULT_LIMIT = 10;

    public EmpresaService(EmpresaRepository repository) {
        this.repository = repository;
    }

    public List<Empresa> obtenerTodas() {
        return obtenerTodas(DEFAULT_LIMIT);
    }

    public List<Empresa> obtenerTodas(Integer limit) {
        int pageSize = (limit != null && limit > 0) ? Math.min(limit, 100) : DEFAULT_LIMIT; // Máximo 100
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Empresa> page = repository.findAllOrderedByCreatedAtDesc(pageable);
        return page.getContent();
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
