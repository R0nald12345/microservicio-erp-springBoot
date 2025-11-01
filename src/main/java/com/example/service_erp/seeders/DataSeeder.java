package com.example.service_erp.seeders;

import com.example.service_erp.entities.*;
import com.example.service_erp.repositories.*;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataSeeder {

    private final EmpresaRepository empresaRepository;
    private final OfertaTrabajoRepository ofertaRepository;
    private final PostulacionRepository postulacionRepository;
    private final EntrevistaRepository entrevistaRepository;
    private final EvaluacionRepository evaluacionRepository;
    private final VisualizacionOfertaRepository visualizacionRepository;

    @PostConstruct
    public void init() {
        if (empresaRepository.count() > 0) {
            System.out.println("📦 Datos ya existen, no se generarán nuevos.");
            return;
        }

        System.out.println("🚀 Cargando datos iniciales (seeders)...");

        // === Empresas ===
        Empresa empresa1 = Empresa.builder()
                .id(UUID.randomUUID())
                .nombre("Tech Solutions S.A.")
                .correo("contacto@techsolutions.com")
                .rubro("Tecnología")
                .build();

        Empresa empresa2 = Empresa.builder()
                .id(UUID.randomUUID())
                .nombre("Finanzas Globales")
                .correo("info@finanzasglobales.com")
                .rubro("Finanzas")
                .build();

        empresaRepository.saveAll(List.of(empresa1, empresa2));

        // === Ofertas de trabajo ===
        OfertaTrabajo oferta1 = OfertaTrabajo.builder()
                .id(UUID.randomUUID())
                .titulo("Desarrollador Backend Java")
                .descripcion("Se busca desarrollador con experiencia en Spring Boot y GraphQL.")
                .salario(2500.0)
                .empresa(empresa1)
                .build();

        OfertaTrabajo oferta2 = OfertaTrabajo.builder()
                .id(UUID.randomUUID())
                .titulo("Analista Financiero Junior")
                .descripcion("Puesto ideal para recién egresados con conocimientos en contabilidad.")
                .salario(1800.0)
                .empresa(empresa2)
                .build();

        ofertaRepository.saveAll(List.of(oferta1, oferta2));

        // === Postulaciones ===
        Postulacion postulacion1 = Postulacion.builder()
                .id(UUID.randomUUID())
                .nombre("Juan Pérez")
                .aniosExperiencia(3)
                .nivelEducacion("Licenciatura en Ingeniería de Software")
                .habilidades("Java, Spring Boot, SQL")
                .idiomas("Español, Inglés")
                .certificaciones("Oracle Java SE 11")
                .puestoActual("Desarrollador Junior")
                .urlCv("https://cvstorage.com/juanperez")
                .fechaPostulacion("2025-10-30")
                .estado("En revisión")
                .oferta(oferta1)
                .build();

        postulacionRepository.save(postulacion1);

        // === Entrevistas ===
        Entrevista entrevista1 = Entrevista.builder()
                .id(UUID.randomUUID())
                .fecha("2025-11-05")
                .duracionMin(60)
                .objetivosTotales("Evaluar habilidades técnicas y comunicación")
                .objetivosCubiertos("Parte técnica completa, faltan soft skills")
                .entrevistador("Carlos Ruiz")
                .postulacion(postulacion1)
                .build();

        entrevistaRepository.save(entrevista1);

        // === Evaluaciones ===
        Evaluacion evaluacion1 = Evaluacion.builder()
                .id(UUID.randomUUID())
                .calificacionTecnica(8.5)
                .calificacionActitud(9.0)
                .calificacionGeneral(8.7)
                .comentarios("Buen candidato, domina Java y tiene actitud proactiva.")
                .entrevista(entrevista1)
                .build();

        evaluacionRepository.save(evaluacion1);

        // === Visualizaciones de ofertas ===
        VisualizacionOferta visualizacion1 = VisualizacionOferta.builder()
                .id(UUID.randomUUID())
                .fechaVisualizacion("2025-10-31")
                .origen("LinkedIn")
                .oferta(oferta1)
                .build();

        visualizacionRepository.save(visualizacion1);

        System.out.println("✅ Datos iniciales cargados correctamente.");
    }
}
