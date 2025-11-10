package com.example.service_erp.repositories;

import com.example.service_erp.entities.OfertaTrabajo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface OfertaTrabajoRepository extends JpaRepository<OfertaTrabajo, UUID> {
    List<OfertaTrabajo> findByTituloContainingIgnoreCase(String titulo);
    
    @Query("SELECT o FROM OfertaTrabajo o ORDER BY o.createdAt DESC")
    Page<OfertaTrabajo> findAllOrderedByCreatedAtDesc(Pageable pageable);
    
    @Query("SELECT o FROM OfertaTrabajo o WHERE o.empresa.id = :empresaId ORDER BY o.createdAt DESC")
    Page<OfertaTrabajo> findByEmpresaIdOrderedByCreatedAtDesc(UUID empresaId, Pageable pageable);
}
