# Integración con Webhooks - n8n (Telegram y Discord)

Este documento describe cómo está configurada la integración HTTP con webhooks externos, específicamente con n8n para Telegram y Discord.

## Cambios Implementados

### 1. Nuevo Servicio: `HttpIntegrationService`
Ubicación: `src/main/java/com/example/service_erp/services/HttpIntegrationService.java`

Este servicio maneja todas las llamadas HTTP a webhooks externos:
- Construye headers HTTP apropiados (Content-Type: application/json)
- Maneja errores y excepciones
- Registra logs de éxito y error
- Retorna un booleano indicando si la operación fue exitosa

```java
public boolean sendToWebhook(String webhookUrl, Map<String, Object> payload)
```

### 2. Actualización: `PostulacionService`
Se agregó la integración con el webhook de Telegram:

**Nuevo método:**
```java
private void enviarAlWebhookTelegram(Postulacion postulacion)
```

**Datos enviados:**
- nombre
- aniosExperiencia
- nivelEducacion
- habilidades
- idiomas
- certificaciones
- puestoActual
- urlCv
- fechaPostulacion
- estado
- ofertaId

### 3. Actualización: `OfertaTrabajoService`
Se agregó la integración con el webhook de Discord:

**Nuevo método:**
```java
private void enviarAlWebhookDiscord(OfertaTrabajo oferta)
```

**Datos enviados:**
- titulo
- descripcion
- salario
- ubicacion
- requisitos
- fechaPublicacion
- empresaNombre
- empresaId
- ofertaId

### 4. Configuración: `GraphQLConfig`
Se agregó un bean de `RestTemplate` para manejar las llamadas HTTP.

### 5. Variables de Entorno
Agregadas en `application.properties`:
```properties
webhook.telegram.url=https://automatization-n8n-n8n.hnlumc.easypanel.host/webhook/telegram
webhook.discord.url=https://automatization-n8n-n8n.hnlumc.easypanel.host/webhook/upload_discord
```

## Flujo de Funcionamiento

### Postulaciones (Telegram)
1. **Crear Postulación**: Cuando se crea una nueva postulación vía GraphQL
2. **Guardar en BD**: La postulación se guarda en la base de datos
3. **Validar Webhook**: Se verifica que la URL del webhook esté configurada
4. **Enviar Datos**: Se realiza un POST con los datos de la postulación
5. **Registrar Resultado**: Se registra el éxito o error en los logs

### Ofertas Laborales (Discord)
1. **Crear Oferta**: Cuando se crea una nueva oferta laboral vía GraphQL
2. **Guardar en BD**: La oferta se guarda en la base de datos
3. **Validar Webhook**: Se verifica que la URL del webhook esté configurada
4. **Enviar Datos**: Se realiza un POST con los datos de la oferta
5. **Registrar Resultado**: Se registra el éxito o error en los logs

## Manejo de Errores

- Si el webhook no está configurado, se omite el envío
- Si ocurre un error en la llamada HTTP, se registra pero NO falla la creación de la postulación
- Se utilizan logs (SLF4J) para rastrear problemas:
  - `INFO`: Envío exitoso
  - `WARN`: Status HTTP no 2xx
  - `ERROR`: Excepciones

## Ejemplo de Payloads Enviados

### Postulación (Telegram)
```json
{
  "nombre": "Juan Pérez",
  "aniosExperiencia": 5,
  "nivelEducacion": "Licenciatura en Sistemas",
  "habilidades": "JavaScript, React, Node.js",
  "idiomas": "Español, Inglés",
  "certificaciones": "AWS Certified Developer",
  "puestoActual": "Desarrollador Frontend",
  "urlCv": "https://ejemplo.com/cv/juanperez.pdf",
  "fechaPostulacion": "2025-11-08T12:00:00Z",
  "estado": "En revisión",
  "ofertaId": "b3b6c8e2-9f4a-4c1e-8c2a-123456789abc"
}
```

### Oferta Laboral (Discord)
```json
{
  "titulo": "Front Developer 2",
  "descripcion": "Buscamos dev React + TS...",
  "salario": 1200.50,
  "ubicacion": "La Paz, Bolivia",
  "requisitos": "React, TypeScript",
  "fechaPublicacion": "2025-11-08T12:00:00Z",
  "empresaNombre": "Tech Company SRL",
  "empresaId": "a1b2c3d4-5e6f-7g8h-9i0j-123456789xyz",
  "ofertaId": "b3b6c8e2-9f4a-4c1e-8c2a-123456789abc"
}
```

## Testing

### Crear Postulación (Telegram)

```graphql
mutation {
  crearPostulacion(
    nombre: "Juan Pérez"
    aniosExperiencia: 5
    nivelEducacion: "Licenciatura en Sistemas"
    habilidades: "JavaScript, React, Node.js"
    idiomas: "Español, Inglés"
    certificaciones: "AWS Certified Developer"
    puestoActual: "Desarrollador Frontend"
    urlCv: "https://ejemplo.com/cv/juanperez.pdf"
    fechaPostulacion: "2025-11-08T12:00:00Z"
    estado: "En revisión"
    telefono: "123456789"
    email: "juan@ejemplo.com"
    ofertaId: "b3b6c8e2-9f4a-4c1e-8c2a-123456789abc"
  ) {
    id
    nombre
    email
  }
}
```

### Crear Oferta Laboral (Discord)

```graphql
mutation {
  crearOferta(
    titulo: "Front Developer 2"
    descripcion: "Buscamos dev React + TS..."
    salario: 1200.50
    ubicacion: "La Paz, Bolivia"
    requisitos: "React, TypeScript"
    fechaPublicacion: "2025-11-08T12:00:00Z"
    empresaId: "a1b2c3d4-5e6f-7g8h-9i0j-123456789xyz"
  ) {
    id
    titulo
    salario
    empresaNombre
  }
}
```

## Próximos Pasos

Para agregar más webhooks:

1. Agregar nuevas propiedades en `application.properties`:
   ```properties
   webhook.otro-servicio.url=https://...
   ```

2. En el servicio correspondiente, agregar los atributos:
   ```java
   @Autowired
   private HttpIntegrationService httpIntegrationService;

   @Value("${webhook.otro-servicio.url:}")
   private String otroWebhookUrl;
   ```

3. Crear un nuevo método privado `enviarAlOtroServicio()` y llamarlo en `crear()`

4. Usar el mismo `HttpIntegrationService` para hacer la llamada

## Archivos Modificados

- ✅ `src/main/java/com/example/service_erp/services/PostulacionService.java`
- ✅ `src/main/java/com/example/service_erp/services/OfertaTrabajoService.java` (actualizado)
- ✅ `src/main/java/com/example/service_erp/services/HttpIntegrationService.java` (nuevo)
- ✅ `src/main/java/com/example/service_erp/config/GraphQLConfig.java`
- ✅ `src/main/resources/application.properties`
