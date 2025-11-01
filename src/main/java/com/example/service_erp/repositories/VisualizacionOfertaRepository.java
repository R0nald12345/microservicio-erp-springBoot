package com.example.service_erp.repositories;

import com.example.service_erp.entities.VisualizacionOferta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface VisualizacionOfertaRepository extends JpaRepository<VisualizacionOferta, UUID> {
    List<VisualizacionOferta> findByOrigen(String origen);
}
