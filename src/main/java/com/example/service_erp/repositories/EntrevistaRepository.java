package com.example.service_erp.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.service_erp.entities.Entrevista;

@Repository
public interface EntrevistaRepository extends JpaRepository<Entrevista, UUID> {
    
    @Query("SELECT e FROM Entrevista e WHERE e.postulacion.id = :postulacionId ORDER BY e.createdAt DESC")
    Page<Entrevista> findByPostulacionIdOrderedByCreatedAtDesc(@Param("postulacionId") UUID postulacionId, Pageable pageable);

    @Query("SELECT e FROM Entrevista e ORDER BY e.createdAt DESC")
    Page<Entrevista> findAllOrderedByCreatedAtDesc(Pageable pageable);
}