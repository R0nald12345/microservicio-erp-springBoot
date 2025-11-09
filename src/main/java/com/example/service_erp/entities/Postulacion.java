package com.example.service_erp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
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
    private UUID id;

    private String nombre;
    private int aniosExperiencia;
    private String nivelEducacion;
    private String habilidades;
    private String idiomas;
    private String certificaciones;
    private String puestoActual;
    private String urlCv;
    private String fechaPostulacion;
    private String estado;
    private String telefono;
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "oferta_id", nullable = false)
    @ToString.Exclude
    public OfertaTrabajo oferta; // Público para acceso desde resolvers (Lombok genera getter pero IDE no lo reconoce)

    @OneToMany(mappedBy = "postulacion", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<Entrevista> entrevistas;
}
