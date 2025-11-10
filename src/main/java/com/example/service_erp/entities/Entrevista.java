package com.example.service_erp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
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
    @Column(name = "id")
    private UUID id;

    @Column(name = "fecha")
    private String fecha;

    @Column(name = "duracion_min")
    private Integer duracionMin;

    @Column(name = "objetivos_totales")
    private String objetivosTotales;

    @Column(name = "objetivos_cubiertos")
    private String objetivosCubiertos;

    @Column(name = "entrevistador")
    private String entrevistador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "postulacion_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    public Postulacion postulacion; // Público para acceso desde resolvers, @JsonIgnore para evitar bucles infinitos

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "entrevista", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    public List<Evaluacion> evaluaciones; // Público para acceso desde resolvers (Lombok genera getter pero IDE no lo reconoce)
}
