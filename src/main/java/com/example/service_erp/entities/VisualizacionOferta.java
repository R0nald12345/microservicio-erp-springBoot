package com.example.service_erp.entities;

import jakarta.persistence.*;
import lombok.*;
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
    private UUID id;

    private String fechaVisualizacion;
    private String origen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oferta_id", nullable = false)
    @ToString.Exclude
    private OfertaTrabajo oferta;
}