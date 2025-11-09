package com.example.service_erp.services;

import com.example.service_erp.entities.OfertaTrabajo;
import com.example.service_erp.entities.Postulacion;
import com.example.service_erp.repositories.OfertaTrabajoRepository;
import com.example.service_erp.repositories.PostulacionRepository;
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
public class PostulacionService {

    private final PostulacionRepository repository;
    private final OfertaTrabajoRepository ofertaRepository;
    
    @Autowired
    private HttpIntegrationService httpIntegrationService;

    @Value("${webhook.telegram.url:}")
    private String telegramWebhookUrl;

    public PostulacionService(PostulacionRepository repository, OfertaTrabajoRepository ofertaRepository) {
        this.repository = repository;
        this.ofertaRepository = ofertaRepository;
    }

    public List<Postulacion> obtenerTodas() {
        return repository.findAll();
    }

    public Postulacion obtenerPorId(UUID id) {
        return repository.findById(id).orElse(null);
    }

    public Postulacion crear(String nombre, int aniosExperiencia, String nivelEducacion,
                             String habilidades, String idiomas, String certificaciones,
                             String puestoActual, String urlCv, String fechaPostulacion,
                             String estado, String telefono, String email, UUID ofertaId) {

        OfertaTrabajo oferta = ofertaRepository.findById(ofertaId)
                .orElseThrow(() -> new RuntimeException("Oferta no encontrada"));

        Postulacion postulacion = Postulacion.builder()
                .nombre(nombre)
                .aniosExperiencia(aniosExperiencia)
                .nivelEducacion(nivelEducacion)
                .habilidades(habilidades)
                .idiomas(idiomas)
                .certificaciones(certificaciones)
                .puestoActual(puestoActual)
                .urlCv(urlCv)
                .fechaPostulacion(fechaPostulacion)
                .estado(estado)
                .telefono(telefono)
                .email(email)
                .oferta(oferta)
                .build();

        Postulacion postulacionGuardada = repository.save(postulacion);
        
        // Enviar datos al webhook de Telegram si está configurado
        if (telegramWebhookUrl != null && !telegramWebhookUrl.isEmpty()) {
            enviarAlWebhookTelegram(postulacionGuardada);
        }

        return postulacionGuardada;
    }

    /**
     * Envía los datos de la postulación al webhook de Telegram
     */
    private void enviarAlWebhookTelegram(Postulacion postulacion) {
        try {
            Map<String, Object> payload = new HashMap<>();
            payload.put("nombre", postulacion.getNombre());
            payload.put("aniosExperiencia", postulacion.getAniosExperiencia());
            payload.put("nivelEducacion", postulacion.getNivelEducacion());
            payload.put("habilidades", postulacion.getHabilidades());
            payload.put("idiomas", postulacion.getIdiomas());
            payload.put("certificaciones", postulacion.getCertificaciones());
            payload.put("puestoActual", postulacion.getPuestoActual());
            payload.put("urlCv", postulacion.getUrlCv());
            payload.put("fechaPostulacion", postulacion.getFechaPostulacion());
            payload.put("estado", postulacion.getEstado());
            payload.put("ofertaId", postulacion.getOferta().getId().toString());

            boolean exito = httpIntegrationService.sendToWebhook(telegramWebhookUrl, payload);
            
            if (exito) {
                log.info("Postulación enviada exitosamente al webhook de Telegram - ID: {}", postulacion.getId());
            } else {
                log.warn("No se pudo enviar la postulación al webhook de Telegram - ID: {}", postulacion.getId());
            }
        } catch (Exception e) {
            log.error("Error al enviar postulación al webhook de Telegram: {}", e.getMessage(), e);
        }
    }

    public void eliminar(UUID id) {
        repository.deleteById(id);
    }
}
