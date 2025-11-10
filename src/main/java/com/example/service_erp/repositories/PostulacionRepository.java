package com.example.service_erp.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.service_erp.entities.Postulacion;

@Repository
public interface PostulacionRepository extends JpaRepository<Postulacion, UUID> {
    
    @Query("SELECT p FROM Postulacion p WHERE p.oferta.id = :ofertaId ORDER BY p.createdAt DESC")
    Page<Postulacion> findByOfertaIdOrderedByCreatedAtDesc(@Param("ofertaId") UUID ofertaId, Pageable pageable);

    @Query("SELECT p FROM Postulacion p ORDER BY p.createdAt DESC")
    Page<Postulacion> findAllOrderedByCreatedAtDesc(Pageable pageable);
}