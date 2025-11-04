package com.example.service_erp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostulanteFeaturesDTO {
    private UUID idPostulante;
    private int aniosExperiencia;
    private String nivelEducacion;
    private String habilidades;
    private String idiomas;
    private String certificaciones;
    private String puestoActual;
    private UUID idOfertaTrabajo;
}