package com.example.service_erp.repositories;


import com.example.service_erp.entities.Entrevista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface EntrevistaRepository extends JpaRepository<Entrevista, UUID> {
    List<Entrevista> findByEntrevistadorContainingIgnoreCase(String nombre);
}