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
- Campos: id (UUID), nombre, correo, rubro

### 2. OfertaTrabajo
- Ofertas de trabajo publicadas por empresas
- Campos: id (UUID), titulo, descripcion, salario, ubicacion, requisitos, fechaPublicacion
- Relación: Pertenece a una Empresa

### 3. Postulacion
- Postulaciones de candidatos a ofertas de trabajo
- Campos: id (UUID), nombre, aniosExperiencia, nivelEducacion, habilidades, idiomas, certificaciones, puestoActual, urlCv, fechaPostulacion, estado, telefono, email
- Relación: Pertenece a una OfertaTrabajo

### 4. Entrevista
- Entrevistas realizadas a postulantes
- Campos: id (UUID), fecha, duracionMin, objetivosTotales, objetivosCubiertos, entrevistador
- Relación: Pertenece a una Postulacion

### 5. Evaluacion
- Evaluaciones de las entrevistas realizadas
- Campos: id (UUID), calificacionTecnica, calificacionActitud, calificacionGeneral, comentarios
- Relación: Pertenece a una Entrevista

### 6. VisualizacionOferta
- Registro de visualizaciones de ofertas de trabajo
- Campos: id (UUID), fechaVisualizacion, origen
- Relación: Pertenece a una OfertaTrabajo

## 📊 API GraphQL

### Queries Disponibles

#### Empresas

**Obtener todas las empresas:**
```graphql
query {
  obtenerEmpresas {
    id
    nombre
    correo
    rubro
  }
}
```

**Obtener empresa por ID:**
```graphql
query {
  obtenerEmpresaPorId(id: "uuid-aqui") {
    id
    nombre
    correo
    rubro
  }
}
```

#### Ofertas de Trabajo

**Obtener todas las ofertas:**
```graphql
query {
  obtenerOfertasTrabajo {
    id
    titulo
    descripcion
    salario
    ubicacion
    requisitos
    fechaPublicacion
    empresa {
      id
      nombre
      correo
    }
  }
}
```

**Obtener oferta por ID:**
```graphql
query {
  obtenerOfertaTrabajoPorId(id: "uuid-aqui") {
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

#### Postulaciones

**Obtener todas las postulaciones:**
```graphql
query {
  obtenerPostulaciones {
    id
    nombre
    puestoActual
    oferta {
      titulo
      empresa {
        nombre
      }
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
    puestoActual
    oferta {
      titulo
    }
  }
}
```

#### Entrevistas

**Obtener todas las entrevistas:**
```graphql
query {
  obtenerEntrevistas {
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
```

**Obtener entrevista por ID:**
```graphql
query {
  obtenerEntrevistaPorId(id: "uuid-aqui") {
    id
    fecha
    duracionMin
    entrevistador
  }
}
```

#### Evaluaciones

**Obtener todas las evaluaciones:**
```graphql
query {
  obtenerEvaluaciones {
    id
    calificacionTecnica
    calificacionActitud
    calificacionGeneral
    comentarios
    entrevista {
      fecha
      entrevistador
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
    calificacionGeneral
    comentarios
  }
}
```

#### Visualizaciones

**Obtener todas las visualizaciones:**
```graphql
query {
  obtenerVisualizacionesOferta {
    id
    fechaVisualizacion
    origen
    oferta {
      titulo
      empresa {
        nombre
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
Empresa
  └── OfertaTrabajo
        ├── Postulacion
        │     └── Entrevista
        │           └── Evaluacion
        └── VisualizacionOferta
```

## 📝 Notas

- Todos los IDs son de tipo **UUID**
- Las fechas se manejan como **String** (formato: "YYYY-MM-DD")
- Los campos marcados con `!` en el schema son **obligatorios**
- El endpoint GraphQL solo acepta peticiones **POST**
