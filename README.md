# Service ERP - Spring Boot GraphQL Recursos Humanos

Microservicio de gestión de recursos humanos construido con Spring Boot y GraphQL para el manejo de empresas, ofertas de trabajo, postulaciones, entrevistas, evaluaciones y visualizaciones.

## 🏗️ Arquitectura

- **Framework:** Spring Boot 3.5.6
- **Java:** 17
- **GraphQL:** Spring GraphQL
- **Base de datos:** PostgreSQL
- **Puerto:** 8080
- **Context Path:** /api
- **Tipo:** Recursos Humanos & ERP

## 📁 Estructura del Proyecto

```
service_erp/
├── src/main/java/com/example/service_erp/
│   ├── ServiceErpApplication.java          # Aplicación principal
│   ├── config/
│   │   └── GraphQLConfig.java             # Configuración GraphQL (scalars)
│   ├── entities/                          # Entidades JPA
│   │   ├── Empresa.java
│   │   ├── OfertaTrabajo.java
│   │   ├── Postulacion.java
│   │   ├── Entrevista.java
│   │   ├── Evaluacion.java
│   │   └── VisualizacionOferta.java
│   ├── repositories/                      # Repositorios JPA
│   │   ├── EmpresaRepository.java
│   │   ├── OfertaTrabajoRepository.java
│   │   ├── PostulacionRepository.java
│   │   ├── EntrevistaRepository.java
│   │   ├── EvaluacionRepository.java
│   │   └── VisualizacionOfertaRepository.java
│   ├── services/                          # Lógica de negocio
│   │   ├── EmpresaService.java
│   │   ├── OfertaTrabajoService.java
│   │   ├── PostulacionService.java
│   │   ├── EntrevistaService.java
│   │   ├── EvaluacionService.java
│   │   └── VisualizacionOfertaService.java
│   ├── resolvers/                         # Resolvers GraphQL
│   │   ├── EmpresaResolver.java
│   │   ├── OfertaTrabajoResolver.java
│   │   ├── PostulacionResolver.java
│   │   ├── EntrevistaResolver.java
│   │   ├── EvaluacionResolver.java
│   │   └── VisualizacionOfertaResolver.java
│   └── seeders/                           # Datos iniciales (si aplica)
├── src/main/resources/
│   ├── application.yml                    # Configuración de la aplicación
│   ├── application.properties            # Propiedades adicionales
│   └── graphql/
│       └── schema.graphqls                # Esquema GraphQL
├── pom.xml                                # Dependencias Maven
└── README.md
```

## 🚀 Inicio Rápido

### Desarrollo Local

1. **Configurar variables de entorno:**

   ```bash
   SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/service_erp
   SPRING_DATASOURCE_USERNAME=tu_usuario
   SPRING_DATASOURCE_PASSWORD=tu_contraseña
   ```

2. **Compilar el proyecto:**

   ```bash
   mvn clean compile
   ```
    
   ```bash
   .\mvnw.cmd clean compile
   ``` 

3. **Ejecutar el servidor:**

   ```bash
   mvn spring-boot:run
   ```
   ```bash
   .\mvnw.cmd spring-boot:run
   ``` 

4. **Acceder a los endpoints:**
   - **GraphiQL (Interfaz Visual):** http://localhost:8080/api/graphiql
   - **GraphQL Endpoint:** http://localhost:8080/api/graphql
   - **Health Check:** http://localhost:8080/api/actuator/health

## 📊 Entidades del Sistema

### 1. Empresa
- Gestión de empresas que publican ofertas de trabajo
- Campos: id (UUID), nombre, correo, rubro, created_at, updated_at
- Relación: Una empresa tiene muchas ofertas de trabajo (OneToMany)

### 2. OfertaTrabajo
- Ofertas de trabajo publicadas por empresas
- Campos: id (UUID), titulo, descripcion, salario, ubicacion, requisitos, fecha_publicacion, empresa_id, created_at, updated_at
- Relaciones: 
  - Pertenece a una Empresa (ManyToOne)
  - Tiene muchas Postulaciones (OneToMany)
  - Tiene muchas VisualizacionesOferta (OneToMany)

### 3. Postulacion
- Postulaciones de candidatos a ofertas de trabajo
- Campos: id (UUID), nombre, anios_experiencia, nivel_educacion, habilidades, idiomas, certificaciones, puesto_actual, url_cv, fecha_postulacion, estado, telefono, email, oferta_id, created_at, updated_at
- Relaciones:
  - Pertenece a una OfertaTrabajo (ManyToOne)
  - Tiene muchas Entrevistas (OneToMany)

### 4. Entrevista
- Entrevistas realizadas a postulantes
- Campos: id (UUID), fecha, duracion_min, objetivos_totales, objetivos_cubiertos, entrevistador, postulacion_id, created_at, updated_at
- Relaciones:
  - Pertenece a una Postulacion (ManyToOne)
  - Tiene muchas Evaluaciones (OneToMany)

### 5. Evaluacion
- Evaluaciones de las entrevistas realizadas
- Campos: id (UUID), calificacion_tecnica, calificacion_actitud, calificacion_general, comentarios, entrevista_id, created_at, updated_at
- Relación: Pertenece a una Entrevista (ManyToOne)

### 6. VisualizacionOferta
- Registro de visualizaciones de ofertas de trabajo
- Campos: id (UUID), fecha_visualizacion, origen, oferta_id, created_at, updated_at
- Relación: Pertenece a una OfertaTrabajo (ManyToOne)

## 📊 API GraphQL

### Queries Disponibles

#### Empresas

**Obtener todas las empresas (con paginación - por defecto 10, ordenadas por fecha DESC):**
```graphql
query {
  obtenerEmpresas(limit: 10) {
    id
    nombre
    correo
    rubro
    createdAt
    updatedAt
    ofertas(limit: 10) {
      id
      titulo
    }
  }
}
```

**Nota:** Si no especificas `limit`, por defecto se devuelven los últimos 10 registros (ordenados por `created_at DESC`). El máximo permitido es 100.

**Obtener empresa por ID:**
```graphql
query {
  obtenerEmpresaPorId(id: "uuid-aqui") {
    id
    nombre
    correo
    rubro
    createdAt
    updatedAt
    ofertas {
      id
      titulo
      descripcion
    }
  }
}
```

#### Ofertas de Trabajo

**Obtener todas las ofertas (con paginación - por defecto 10, ordenadas por fecha DESC):**
```graphql
query {
  obtenerOfertasTrabajo(limit: 10) {
    id
    titulo
    descripcion
    salario
    ubicacion
    requisitos
    fechaPublicacion
    createdAt
    updatedAt
    empresa {
      id
      nombre
      correo
      rubro
    }
    postulaciones(limit: 10) {
      id
      nombre
      estado
    }
    visualizaciones(limit: 10) {
      id
      fechaVisualizacion
      origen
    }
  }
}
```

**Nota:** Usa `limit` en las relaciones para evitar cargar miles de registros. Por defecto cada relación devuelve 10 registros.

**Obtener oferta por ID:**
```graphql
query {
  obtenerOfertaTrabajoPorId(id: "uuid-aqui") {
    id
    titulo
    descripcion
    salario
    ubicacion
    requisitos
    fechaPublicacion
    createdAt
    updatedAt
    empresa {
      id
      nombre
      correo
      rubro
    }
    postulaciones {
      id
      nombre
      estado
    }
    visualizaciones {
      id
      fechaVisualizacion
      origen
    }
  }
}
```

#### Postulaciones

**Obtener todas las postulaciones (con paginación - por defecto 10, ordenadas por fecha DESC):**
```graphql
query {
  obtenerPostulaciones(limit: 10) {
    id
    nombre
    aniosExperiencia
    nivelEducacion
    habilidades
    idiomas
    certificaciones
    puestoActual
    urlCv
    fechaPostulacion
    estado
    telefono
    email
    createdAt
    updatedAt
    oferta {
      id
      titulo
      empresa {
        id
        nombre
        correo
      }
    }
    entrevistas(limit: 10) {
      id
      fecha
      entrevistador
    }
  }
}
```

**Obtener postulación por ID:**
```graphql
query {
  obtenerPostulacionPorId(id: "uuid-aqui") {
    id
    nombre
    aniosExperiencia
    nivelEducacion
    habilidades
    idiomas
    certificaciones
    puestoActual
    urlCv
    fechaPostulacion
    estado
    telefono
    email
    createdAt
    updatedAt
    oferta {
      id
      titulo
      descripcion
      empresa {
        nombre
        correo
      }
    }
    entrevistas {
      id
      fecha
      duracionMin
      entrevistador
    }
  }
}
```

#### Entrevistas

**Obtener todas las entrevistas (con paginación - por defecto 10, ordenadas por fecha DESC):**
```graphql
query {
  obtenerEntrevistas(limit: 10) {
    id
    fecha
    duracionMin
    objetivosTotales
    objetivosCubiertos
    entrevistador
    createdAt
    updatedAt
    postulacion {
      id
      nombre
      puestoActual
      oferta {
        id
        titulo
        empresa {
          nombre
        }
      }
    }
    evaluaciones(limit: 10) {
      id
      calificacionTecnica
      calificacionActitud
      calificacionGeneral
    }
  }
}
```

**Obtener entrevista por ID:**
```graphql
query {
  obtenerEntrevistaPorId(id: "uuid-aqui") {
    id
    fecha
    duracionMin
    objetivosTotales
    objetivosCubiertos
    entrevistador
    createdAt
    updatedAt
    postulacion {
      id
      nombre
      puestoActual
      oferta {
        titulo
      }
    }
    evaluaciones {
      id
      calificacionTecnica
      calificacionActitud
      calificacionGeneral
      comentarios
    }
  }
}
```

#### Evaluaciones

**Obtener todas las evaluaciones (con paginación - por defecto 10, ordenadas por fecha DESC):**
```graphql
query {
  obtenerEvaluaciones(limit: 10) {
    id
    calificacionTecnica
    calificacionActitud
    calificacionGeneral
    comentarios
    createdAt
    updatedAt
    entrevista {
      id
      fecha
      duracionMin
      entrevistador
      postulacion {
        nombre
        puestoActual
      }
    }
  }
}
```

**Obtener evaluación por ID:**
```graphql
query {
  obtenerEvaluacionPorId(id: "uuid-aqui") {
    id
    calificacionTecnica
    calificacionActitud
    calificacionGeneral
    comentarios
    createdAt
    updatedAt
    entrevista {
      id
      fecha
      duracionMin
      entrevistador
      postulacion {
        nombre
        oferta {
          titulo
        }
      }
    }
  }
}
```

#### Visualizaciones

**Obtener todas las visualizaciones (con paginación - por defecto 10, ordenadas por fecha DESC):**
```graphql
query {
  obtenerVisualizacionesOferta(limit: 10) {
    id
    fechaVisualizacion
    origen
    createdAt
    updatedAt
    oferta {
      id
      titulo
      descripcion
      empresa {
        id
        nombre
        correo
        rubro
      }
    }
  }
}
```

**Obtener visualización por ID:**
```graphql
query {
  obtenerVisualizacionOfertaPorId(id: "uuid-aqui") {
    id
    fechaVisualizacion
    origen
    createdAt
    updatedAt
    oferta {
      id
      titulo
      descripcion
      empresa {
        nombre
        correo
      }
    }
  }
}
```

### Mutations Disponibles

#### Crear Empresa

```graphql
mutation {
  crearEmpresa(
    nombre: "Tech Solutions S.A."
    correo: "contacto@techsolutions.com"
    rubro: "Tecnología"
  ) {
    id
    nombre
    correo
    rubro
  }
}
```

#### Eliminar Empresa

```graphql
mutation {
  eliminarEmpresa(id: "uuid-aqui")
}
```

#### Crear Oferta de Trabajo

```graphql
mutation {
  crearOfertaTrabajo(
    titulo: "Desarrollador Full Stack"
    descripcion: "Buscamos desarrollador con experiencia en Spring Boot y React"
    salario: 5000.0
    ubicacion: "La Paz, Bolivia"
    requisitos: "Java, Spring Boot, React, PostgreSQL"
    fechaPublicacion: "2025-11-01"
    empresaId: "uuid-empresa"
  ) {
    id
    titulo
    descripcion
    salario
    empresa {
      nombre
    }
  }
}
```

#### Eliminar Oferta de Trabajo

```graphql
mutation {
  eliminarOfertaTrabajo(id: "uuid-aqui")
}
```

#### Crear Postulación

```graphql
mutation {
  crearPostulacion(
    nombre: "María García"
    aniosExperiencia: 5
    nivelEducacion: "Universitaria"
    habilidades: "Java, Spring Boot, React, PostgreSQL"
    idiomas: "Español, Inglés"
    certificaciones: "Oracle Certified Professional"
    puestoActual: "Desarrollador Backend"
    urlCv: "https://example.com/cv/maria-garcia.pdf"
    fechaPostulacion: "2025-11-01"
    estado: "Pendiente"
    telefono: "+591 70012345"
    email: "maria.garcia@example.com"
    ofertaId: "uuid-oferta"
  ) {
    id
    nombre
    puestoActual
    telefono
    email
    oferta {
      titulo
    }
  }
}
```

#### Eliminar Postulación

```graphql
mutation {
  eliminarPostulacion(id: "uuid-aqui")
}
```

#### Crear Entrevista

```graphql
mutation {
  crearEntrevista(
    fecha: "2025-11-15"
    duracionMin: 60
    objetivosTotales: "Evaluar conocimientos técnicos y habilidades"
    objetivosCubiertos: "Java, Spring Boot"
    entrevistador: "Juan Pérez"
    postulacionId: "uuid-postulacion"
  ) {
    id
    fecha
    entrevistador
  }
}
```

#### Eliminar Entrevista

```graphql
mutation {
  eliminarEntrevista(id: "uuid-aqui")
}
```

#### Crear Evaluación

```graphql
mutation {
  crearEvaluacion(
    calificacionTecnica: 8.5
    calificacionActitud: 9.0
    calificacionGeneral: 8.75
    comentarios: "Excelente candidato, muy motivado"
    entrevistaId: "uuid-entrevista"
  ) {
    id
    calificacionTecnica
    calificacionActitud
    calificacionGeneral
    comentarios
  }
}
```

#### Eliminar Evaluación

```graphql
mutation {
  eliminarEvaluacion(id: "uuid-aqui")
}
```

#### Crear Visualización de Oferta

```graphql
mutation {
  crearVisualizacionOferta(
    fechaVisualizacion: "2025-11-01"
    origen: "LinkedIn"
    ofertaId: "uuid-oferta"
  ) {
    id
    fechaVisualizacion
    origen
  }
}
```

#### Eliminar Visualización

```graphql
mutation {
  eliminarVisualizacionOferta(id: "uuid-aqui")
}
```

## 📝 Operaciones de Actualización (UPDATE)

### Mutations para Editar

#### Editar Empresa

```graphql
mutation {
  actualizarEmpresa(
    id: "uuid-aqui"
    nombre: "Nuevo nombre"
    correo: "nuevo@correo.com"
    rubro: "Nuevo rubro"
  ) {
    id
    nombre
    correo
    rubro
    updatedAt
  }
}
```

#### Editar Oferta de Trabajo

```graphql
mutation {
  actualizarOfertaTrabajo(
    id: "uuid-aqui"
    titulo: "Nuevo título"
    descripcion: "Nueva descripción"
    salario: 6000.0
    ubicacion: "Nuevo lugar"
    requisitos: "Nuevos requisitos"
    fechaPublicacion: "2025-11-15"
  ) {
    id
    titulo
    descripcion
    salario
    ubicacion
    requisitos
    fechaPublicacion
    updatedAt
  }
}
```

#### Editar Postulación

```graphql
mutation {
  actualizarPostulacion(
    id: "uuid-aqui"
    nombre: "Nuevo nombre"
    aniosExperiencia: 7
    nivelEducacion: "Posgrado"
    habilidades: "Java, Kotlin, Spring Boot"
    idiomas: "Español, Inglés, Portugués"
    certificaciones: "Nuevas certificaciones"
    puestoActual: "Senior Developer"
    urlCv: "https://example.com/cv/nuevo.pdf"
    fechaPostulacion: "2025-11-10"
    estado: "En entrevista"
    telefono: "+591 70012346"
    email: "nuevo@example.com"
  ) {
    id
    nombre
    estado
    telefono
    email
    updatedAt
  }
}
```

#### Editar Entrevista

```graphql
mutation {
  actualizarEntrevista(
    id: "uuid-aqui"
    fecha: "2025-11-20"
    duracionMin: 90
    objetivosTotales: "Objetivos actualizados"
    objetivosCubiertos: "Objetivos cubiertos"
    entrevistador: "Nuevo entrevistador"
  ) {
    id
    fecha
    duracionMin
    entrevistador
    updatedAt
  }
}
```

#### Editar Evaluación

```graphql
mutation {
  actualizarEvaluacion(
    id: "uuid-aqui"
    calificacionTecnica: 9.0
    calificacionActitud: 8.5
    calificacionGeneral: 8.75
    comentarios: "Excelente desempeño en la entrevista"
  ) {
    id
    calificacionTecnica
    calificacionActitud
    calificacionGeneral
    comentarios
    updatedAt
  }
}
```

#### Editar Visualización de Oferta

```graphql
mutation {
  actualizarVisualizacionOferta(
    id: "uuid-aqui"
    fechaVisualizacion: "2025-11-12"
    origen: "Twitter"
  ) {
    id
    fechaVisualizacion
    origen
    updatedAt
  }
}
```

### Ejemplos de Uso Práctico

#### Actualizar el estado de una postulación

```graphql
mutation {
  actualizarPostulacion(
    id: "550e8400-e29b-41d4-a716-446655440000"
    estado: "Seleccionado"
  ) {
    id
    nombre
    estado
    email
    updatedAt
  }
}
```

#### Cambiar salario de una oferta

```graphql
mutation {
  actualizarOfertaTrabajo(
    id: "550e8400-e29b-41d4-a716-446655440001"
    salario: 7500.0
  ) {
    id
    titulo
    salario
    updatedAt
  }
}
```

#### Actualizar datos de contacto de empresa

```graphql
mutation {
  actualizarEmpresa(
    id: "550e8400-e29b-41d4-a716-446655440002"
    correo: "contacto.nuevo@empresa.com"
  ) {
    id
    nombre
    correo
    updatedAt
  }
}
```

### Notas sobre Actualizaciones

- ✅ **Campos opcionales:** Puedes actualizar solo los campos que necesites cambiar
- ✅ **Preservación de datos:** Los campos no especificados mantienen sus valores anteriores
- ✅ **Timestamp automático:** El campo `updatedAt` se actualiza automáticamente
- ✅ **Validaciones:** Se aplican las mismas validaciones que en la creación
- ✅ **Relaciones:** No se pueden cambiar las relaciones (foreign keys) mediante mutations de actualización



## 🔧 Configuración

### Base de Datos PostgreSQL

- **Driver:** `org.postgresql.Driver`
- **Configuración:** A través de variables de entorno
- **DDL:** `update` (actualiza el esquema automáticamente)

### Variables de Entorno Requeridas

```bash
# Base de datos
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/service_erp
SPRING_DATASOURCE_USERNAME=tu_usuario
SPRING_DATASOURCE_PASSWORD=tu_contraseña
```

### Configuración GraphQL

- **Endpoint:** `/api/graphql`
- **GraphiQL:** `/api/graphiql`
- **CORS:** Habilitado para todos los orígenes
- **Scalars:** UUID, DateTime, Json

## 🧪 Pruebas

### Usar cURL

```bash
# Health check
curl http://localhost:8080/api/actuator/health

# GraphQL query - Obtener empresas
curl -X POST http://localhost:8080/api/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "query { obtenerEmpresas { id nombre correo } }"}'

# GraphQL mutation - Crear empresa
curl -X POST http://localhost:8080/api/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "mutation { crearEmpresa(nombre: \"Mi Empresa\", correo: \"info@empresa.com\", rubro: \"Tech\") { id nombre } }"}'
```

### Usar GraphiQL

Visita http://localhost:8080/api/graphiql para usar el explorador GraphQL integrado.

### Usar Insomnia / Postman

Consulta el archivo `GUIA_INSOMNIA.md` para ejemplos detallados de cómo probar los endpoints.

## 🔗 Integración con Gateway

Este servicio está diseñado para integrarse con un Gateway GraphQL:

- **URL interna (Docker):** http://service_erp:8080
- **URL desarrollo:** http://localhost:8080
- **Endpoint GraphQL:** `/api/graphql`
- **Health check:** `/api/actuator/health`

## 🚨 Logging

El servicio incluye logging detallado:

- Operaciones CRUD en todas las entidades
- Queries y mutations GraphQL
- Errores y excepciones
- Consultas SQL (cuando `show-sql: true`)

## 📦 Dependencias Principales

- `spring-boot-starter-web` - Framework web
- `spring-boot-starter-graphql` - Soporte GraphQL
- `spring-boot-starter-data-jpa` - Persistencia de datos
- `spring-boot-starter-validation` - Validaciones
- `spring-boot-starter-actuator` - Métricas y health checks
- `postgresql` - Driver PostgreSQL
- `graphql-java-extended-scalars` - Scalars extendidos (UUID, DateTime, Json)
- `lombok` - Reducción de código boilerplate

## 📚 Documentación Adicional

- **Guía de Insomnia:** `GUIA_INSOMNIA.md` - Ejemplos paso a paso para probar endpoints
- **Ejemplos GraphQL:** `EJEMPLOS_GRAPHQL.md` - Colección completa de queries y mutations

## 🏷️ Modelo de Datos

```
Empresa (1) ──< (N) OfertaTrabajo
                      ├── (1) ──< (N) Postulacion
                      │              └── (1) ──< (N) Entrevista
                      │                            └── (1) ──< (N) Evaluacion
                      └── (1) ──< (N) VisualizacionOferta
```

**Multiplicidad:**
- **Empresa** → **OfertaTrabajo**: Una empresa tiene muchas ofertas (1:N)
- **OfertaTrabajo** → **Empresa**: Una oferta pertenece a una empresa (N:1)
- **OfertaTrabajo** → **Postulacion**: Una oferta tiene muchas postulaciones (1:N)
- **Postulacion** → **OfertaTrabajo**: Una postulación pertenece a una oferta (N:1)
- **Postulacion** → **Entrevista**: Una postulación tiene muchas entrevistas (1:N)
- **Entrevista** → **Postulacion**: Una entrevista pertenece a una postulación (N:1)
- **Entrevista** → **Evaluacion**: Una entrevista tiene muchas evaluaciones (1:N)
- **Evaluacion** → **Entrevista**: Una evaluación pertenece a una entrevista (N:1)
- **OfertaTrabajo** → **VisualizacionOferta**: Una oferta tiene muchas visualizaciones (1:N)
- **VisualizacionOferta** → **OfertaTrabajo**: Una visualización pertenece a una oferta (N:1)

## 📝 Notas

- Todos los IDs son de tipo **UUID**
- Las fechas se manejan como **String** (formato: "YYYY-MM-DD")
- Los campos `created_at` y `updated_at` se generan automáticamente y son de tipo **DateTime**
- Los nombres de columnas en PostgreSQL están en **snake_case** (ej: `fecha_publicacion`, `anios_experiencia`)
- Los nombres de campos en las entidades Java están en **camelCase** (ej: `fechaPublicacion`, `aniosExperiencia`)
- Los campos marcados con `!` en el schema son **obligatorios**
- El endpoint GraphQL solo acepta peticiones **POST**
- Las relaciones están correctamente mapeadas según la estructura de PostgreSQL

## ⚡ Optimización y Paginación

### Paginación Automática
- **Por defecto:** Todas las queries devuelven los últimos **10 registros** (ordenados por `created_at DESC`)
- **Parámetro `limit`:** Puedes especificar cuántos registros quieres (máximo 100)
- **Ordenamiento:** Los registros se ordenan por fecha de creación descendente (más recientes primero)

### Ejemplo de uso con paginación:
```graphql
# Obtener últimos 15 empresas
query {
  obtenerEmpresas(limit: 15) {
    id
    nombre
    ofertas(limit: 5) {  # Solo las últimas 5 ofertas por empresa
      id
      titulo
    }
  }
}
```

### ¿Por qué paginación?
- **Rendimiento:** Evita cargar miles de registros de una vez
- **Velocidad:** Las queries son instantáneas incluso con 3000+ registros
- **Escalabilidad:** El sistema puede manejar grandes volúmenes de datos sin problemas
- **UX:** Los usuarios ven primero los datos más recientes


# Construir y levantar los servicios
docker-compose up -d

# Ver logs
docker-compose logs -f service_erp

# Detener los servicios
docker-compose down

# Detener y eliminar volúmenes (¡cuidado, elimina datos!)
docker-compose down -v

# Reconstruir solo la aplicación
docker-compose build service_erp
docker-compose up -d service_erp