package com.example.service_erp.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.service_erp.entities.OfertaTrabajo;
import com.example.service_erp.entities.Postulacion;
import com.example.service_erp.repositories.OfertaTrabajoRepository;
import com.example.service_erp.repositories.PostulacionRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostulacionService {

    private final PostulacionRepository repository;
    private final OfertaTrabajoRepository ofertaRepository;
    private static final int DEFAULT_LIMIT = 10;

    @Autowired
    private HttpIntegrationService httpIntegrationService;

    @Value("${webhook.telegram.url:}")
    private String telegramWebhookUrl;

    public PostulacionService(PostulacionRepository repository, OfertaTrabajoRepository ofertaRepository) {
        this.repository = repository;
        this.ofertaRepository = ofertaRepository;
    }

    @Transactional(readOnly = true)
    public List<Postulacion> obtenerTodas() {
        return obtenerTodas(DEFAULT_LIMIT);
    }

    @Transactional(readOnly = true)
    public List<Postulacion> obtenerTodas(Integer limit) {
        int pageSize = (limit != null && limit > 0) ? Math.min(limit, 100) : DEFAULT_LIMIT;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Postulacion> page = repository.findAllOrderedByCreatedAtDesc(pageable);
        return page.getContent();
    }

    @Transactional(readOnly = true)
    public List<Postulacion> obtenerPorOfertaId(UUID ofertaId, Integer limit) {
        int pageSize = (limit != null && limit > 0) ? Math.min(limit, 100) : DEFAULT_LIMIT;
        Pageable pageable = PageRequest.of(0, pageSize);
        Page<Postulacion> page = repository.findByOfertaIdOrderedByCreatedAtDesc(ofertaId, pageable);
        return page.getContent();
    }

    @Transactional(readOnly = true)
    public Postulacion obtenerPorId(UUID id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
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
        log.info("Postulación creada exitosamente - ID: {}", postulacionGuardada.getId());
        
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

    @Transactional
    public Postulacion actualizar(UUID id, String nombre, Integer aniosExperiencia, String nivelEducacion,
                                  String habilidades, String idiomas, String certificaciones,
                                  String puestoActual, String urlCv, String fechaPostulacion,
                                  String estado, String telefono, String email) {
        Postulacion postulacion = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Postulación no encontrada"));

        if (nombre != null && !nombre.isEmpty()) {
            postulacion.setNombre(nombre);
        }
        if (aniosExperiencia != null) {
            postulacion.setAniosExperiencia(aniosExperiencia);
        }
        if (nivelEducacion != null && !nivelEducacion.isEmpty()) {
            postulacion.setNivelEducacion(nivelEducacion);
        }
        if (habilidades != null && !habilidades.isEmpty()) {
            postulacion.setHabilidades(habilidades);
        }
        if (idiomas != null && !idiomas.isEmpty()) {
            postulacion.setIdiomas(idiomas);
        }
        if (certificaciones != null && !certificaciones.isEmpty()) {
            postulacion.setCertificaciones(certificaciones);
        }
        if (puestoActual != null && !puestoActual.isEmpty()) {
            postulacion.setPuestoActual(puestoActual);
        }
        if (urlCv != null && !urlCv.isEmpty()) {
            postulacion.setUrlCv(urlCv);
        }
        if (fechaPostulacion != null && !fechaPostulacion.isEmpty()) {
            postulacion.setFechaPostulacion(fechaPostulacion);
        }
        if (estado != null && !estado.isEmpty()) {
            postulacion.setEstado(estado);
        }
        if (telefono != null && !telefono.isEmpty()) {
            postulacion.setTelefono(telefono);
        }
        if (email != null && !email.isEmpty()) {
            postulacion.setEmail(email);
        }

        log.info("Postulación actualizada - ID: {}", id);
        return repository.save(postulacion);
    }

    @Transactional
    public void eliminar(UUID id) {
        repository.deleteById(id);
        log.info("Postulación eliminada - ID: {}", id);
    }
}