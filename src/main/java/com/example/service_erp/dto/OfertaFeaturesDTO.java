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
public class OfertaFeaturesDTO {
    private UUID idOferta;
    private String titulo;
    private Double salario;
    private String ubicacion;
    private String requisitos;
    private UUID idEmpresa;
}