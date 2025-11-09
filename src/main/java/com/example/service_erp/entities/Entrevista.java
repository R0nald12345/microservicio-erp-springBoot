package com.example.service_erp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    public Postulacion postulacion; // Público para acceso desde resolvers (Lombok genera getter pero IDE no lo reconoce)

    @OneToMany(mappedBy = "entrevista", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<Evaluacion> evaluaciones;
}
