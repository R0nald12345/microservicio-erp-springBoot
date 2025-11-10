package com.example.service_erp.repositories;

import com.example.service_erp.entities.Evaluacion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface EvaluacionRepository extends JpaRepository<Evaluacion, UUID> {
    @Query("SELECT e FROM Evaluacion e ORDER BY e.createdAt DESC")
    Page<Evaluacion> findAllOrderedByCreatedAtDesc(Pageable pageable);
    
    @Query("SELECT e FROM Evaluacion e WHERE e.entrevista.id = :entrevistaId ORDER BY e.createdAt DESC")
    Page<Evaluacion> findByEntrevistaIdOrderedByCreatedAtDesc(UUID entrevistaId, Pageable pageable);
}
