package com.example.service_erp.repositories;

import com.example.service_erp.entities.VisualizacionOferta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface VisualizacionOfertaRepository extends JpaRepository<VisualizacionOferta, UUID> {
    List<VisualizacionOferta> findByOrigen(String origen);
    
    @Query("SELECT v FROM VisualizacionOferta v ORDER BY v.createdAt DESC")
    Page<VisualizacionOferta> findAllOrderedByCreatedAtDesc(Pageable pageable);
    
    @Query("SELECT v FROM VisualizacionOferta v WHERE v.oferta.id = :ofertaId ORDER BY v.createdAt DESC")
    Page<VisualizacionOferta> findByOfertaIdOrderedByCreatedAtDesc(UUID ofertaId, Pageable pageable);
}
