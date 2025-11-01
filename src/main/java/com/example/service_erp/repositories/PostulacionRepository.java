package com.example.service_erp.repositories;

import com.example.service_erp.entities.Postulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface PostulacionRepository extends JpaRepository<Postulacion, UUID> {
    List<Postulacion> findByEstado(String estado);
}