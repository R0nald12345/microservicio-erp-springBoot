package com.example.service_erp.repositories;

import com.example.service_erp.entities.Entrevista;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface EntrevistaRepository extends JpaRepository<Entrevista, UUID> {
    List<Entrevista> findByEntrevistadorContainingIgnoreCase(String nombre);
    
    @Query("SELECT e FROM Entrevista e ORDER BY e.createdAt DESC")
    Page<Entrevista> findAllOrderedByCreatedAtDesc(Pageable pageable);
    
    @Query("SELECT e FROM Entrevista e WHERE e.postulacion.id = :postulacionId ORDER BY e.createdAt DESC")
    Page<Entrevista> findByPostulacionIdOrderedByCreatedAtDesc(UUID postulacionId, Pageable pageable);
}