package com.example.service_erp.repositories;

import com.example.service_erp.entities.Empresa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {
    Empresa findByNombre(String nombre);
    
    @Query("SELECT e FROM Empresa e ORDER BY e.createdAt DESC")
    Page<Empresa> findAllOrderedByCreatedAtDesc(Pageable pageable);
}
