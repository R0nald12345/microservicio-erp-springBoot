package com.example.service_erp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;


@Entity
@Table(name = "evaluaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "calificacion_tecnica")
    private Double calificacionTecnica;

    @Column(name = "calificacion_actitud")
    private Double calificacionActitud;

    @Column(name = "calificacion_general")
    private Double calificacionGeneral;

    @Column(name = "comentarios", columnDefinition = "TEXT")
    private String comentarios;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entrevista_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    public Entrevista entrevista; // Público para acceso desde resolvers, @JsonIgnore para evitar bucles infinitos

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;
}
