package com.example.service_erp.entities;

import jakarta.persistence.*;
import lombok.*;

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
    private UUID id;

    private Double calificacionTecnica;
    private Double calificacionActitud;
    private Double calificacionGeneral;

    @Column(columnDefinition = "TEXT")
    private String comentarios;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entrevista_id", nullable = false)
    @ToString.Exclude
    private Entrevista entrevista;
}
