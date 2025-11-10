package com.example.service_erp.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;

import java.util.Map;

@Service
@Slf4j
public class HttpIntegrationService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * Envía una solicitud HTTP POST a un webhook con los datos de postulación
     * 
     * @param webhookUrl URL del webhook
     * @param payload datos a enviar
     * @return true si la llamada fue exitosa, false en caso contrario
     */
    public boolean sendToWebhook(String webhookUrl, Map<String, Object> payload) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(webhookUrl, request, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Webhook llamado exitosamente: {} - Status: {}", webhookUrl, response.getStatusCode());
                return true;
            } else {
                log.warn("Webhook retornó status no exitoso: {} - Status: {}", webhookUrl, response.getStatusCode());
                return false;
            }

        } catch (RestClientException e) {
            log.error("Error al llamar webhook {}: {}", webhookUrl, e.getMessage(), e);
            return false;
        } catch (Exception e) {
            log.error("Error inesperado al llamar webhook {}: {}", webhookUrl, e.getMessage(), e);
            return false;
        }
    }
}
