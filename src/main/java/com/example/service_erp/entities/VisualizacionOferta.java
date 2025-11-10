package com.example.service_erp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "visualizaciones_oferta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisualizacionOferta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "fecha_visualizacion")
    private String fechaVisualizacion;

    @Column(name = "origen")
    private String origen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oferta_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    public OfertaTrabajo oferta; // Público para acceso desde resolvers, @JsonIgnore para evitar bucles infinitos

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}