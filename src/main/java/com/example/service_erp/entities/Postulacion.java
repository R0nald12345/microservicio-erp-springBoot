package com.example.service_erp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "postulaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Postulacion {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "anios_experiencia")
    private int aniosExperiencia;

    @Column(name = "nivel_educacion")
    private String nivelEducacion;

    @Column(name = "habilidades")
    private String habilidades;

    @Column(name = "idiomas")
    private String idiomas;

    @Column(name = "certificaciones")
    private String certificaciones;

    @Column(name = "puesto_actual")
    private String puestoActual;

    @Column(name = "url_cv")
    private String urlCv;

    @Column(name = "fecha_postulacion")
    private String fechaPostulacion;

    @Column(name = "estado")
    private String estado;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oferta_id", nullable = false)
    @ToString.Exclude
    public OfertaTrabajo oferta; // Público para acceso desde resolvers (Lombok genera getter pero IDE no lo reconoce)

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "postulacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    public List<Entrevista> entrevistas; // Público para acceso desde resolvers (Lombok genera getter pero IDE no lo reconoce)
}
