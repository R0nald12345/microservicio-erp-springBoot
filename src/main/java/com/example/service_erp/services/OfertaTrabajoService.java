package com.example.service_erp.services;

import com.example.service_erp.entities.Empresa;
import com.example.service_erp.entities.OfertaTrabajo;
import com.example.service_erp.repositories.EmpresaRepository;
import com.example.service_erp.repositories.OfertaTrabajoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class OfertaTrabajoService {

    private final OfertaTrabajoRepository repository;
    private final EmpresaRepository empresaRepository;

    @Autowired
    private HttpIntegrationService httpIntegrationService;

    @Value("${webhook.discord.url:}")
    private String discordWebhookUrl;

    public OfertaTrabajoService(OfertaTrabajoRepository repository, EmpresaRepository empresaRepository) {
        this.repository = repository;
        this.empresaRepository = empresaRepository;
    }

    public List<OfertaTrabajo> obtenerTodas() {
        return repository.findAll();
    }

    public OfertaTrabajo obtenerPorId(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public OfertaTrabajo crear(String titulo, String descripcion, Double salario, String ubicacion,
                               String requisitos, String fechaPublicacion, UUID empresaId) {
        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));

        OfertaTrabajo oferta = OfertaTrabajo.builder()
                .titulo(titulo)
                .descripcion(descripcion)
                .salario(salario)
                .ubicacion(ubicacion)
                .requisitos(requisitos)
                .fechaPublicacion(fechaPublicacion)
                .empresa(empresa)
                .build();

        OfertaTrabajo ofertaGuardada = repository.save(oferta);

        // Enviar datos al webhook de Discord si está configurado
        if (discordWebhookUrl != null && !discordWebhookUrl.isEmpty()) {
            enviarAlWebhookDiscord(ofertaGuardada);
        }

        return ofertaGuardada;
    }

    /**
     * Envía los datos de la oferta laboral al webhook de Discord
     */
    private void enviarAlWebhookDiscord(OfertaTrabajo oferta) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("titulo", oferta.getTitulo());
            payload.put("descripcion", oferta.getDescripcion());
            payload.put("salario", oferta.getSalario());
            payload.put("ubicacion", oferta.getUbicacion());
            payload.put("requisitos", oferta.getRequisitos());
            payload.put("fechaPublicacion", oferta.getFechaPublicacion());
            payload.put("empresaNombre", oferta.getEmpresa().getNombre());
            payload.put("empresaId", oferta.getEmpresa().getId().toString());
            payload.put("ofertaId", oferta.getId().toString());

            boolean exito = httpIntegrationService.sendToWebhook(discordWebhookUrl, payload);

            if (exito) {
                log.info("Oferta laboral enviada exitosamente a Discord - ID: {}", oferta.getId());
            } else {
                log.warn("No se pudo enviar la oferta laboral a Discord - ID: {}", oferta.getId());
            }
        } catch (Exception e) {
            log.error("Error al enviar oferta laboral a Discord: {}", e.getMessage(), e);
        }
    }

    public void eliminar(UUID id) {
        repository.deleteById(id);
    }
}
