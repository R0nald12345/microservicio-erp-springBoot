package com.example.service_erp.repositories;

import com.example.service_erp.entities.OfertaTrabajo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface OfertaTrabajoRepository extends JpaRepository<OfertaTrabajo, UUID> {
    List<OfertaTrabajo> findByTituloContainingIgnoreCase(String titulo);
}
