package com.example.service_erp.entities;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "entrevistas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Entrevista {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fecha;

    private Integer duracionMin;

    private String objetivosTotales;

    private String objetivosCubiertos;

    private String entrevistador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postulacion_id", nullable = false)
    @ToString.Exclude
    private Postulacion postulacion;

    @OneToMany(mappedBy = "entrevista", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Evaluacion> evaluaciones;
}
