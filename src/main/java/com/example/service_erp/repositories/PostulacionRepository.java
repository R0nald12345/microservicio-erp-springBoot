package com.example.service_erp.repositories;

import com.example.service_erp.entities.Postulacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface PostulacionRepository extends JpaRepository<Postulacion, UUID> {
    List<Postulacion> findByEstado(String estado);
    
    @Query("SELECT p FROM Postulacion p ORDER BY p.createdAt DESC")
    Page<Postulacion> findAllOrderedByCreatedAtDesc(Pageable pageable);
    
    @Query("SELECT p FROM Postulacion p WHERE p.oferta.id = :ofertaId ORDER BY p.createdAt DESC")
    Page<Postulacion> findByOfertaIdOrderedByCreatedAtDesc(UUID ofertaId, Pageable pageable);
}