package com.example.service_erp.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ofertas_trabajo")
public class OfertaTrabajo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "salario")
    private Double salario;

    @Column(name = "ubicacion")
    private String ubicacion;

    @Column(name = "requisitos", columnDefinition = "TEXT")
    private String requisitos;

    @Column(name = "fecha_publicacion")
    private String fechaPublicacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    @ToString.Exclude
    @JsonIgnore
    public Empresa empresa; // Público para acceso desde resolvers, @JsonIgnore para evitar bucles infinitos

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    @OneToMany(mappedBy = "oferta", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    public List<Postulacion> postulaciones; // Público para acceso desde resolvers (Lombok genera getter pero IDE no lo reconoce)

    @OneToMany(mappedBy = "oferta", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    public List<VisualizacionOferta> visualizaciones; // Público para acceso desde resolvers (Lombok genera getter pero IDE no lo reconoce)
}
