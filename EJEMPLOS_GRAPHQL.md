# Ejemplos de Queries y Mutations GraphQL

## 🔗 URLs Importantes

- **GraphiQL (Interfaz Visual):** `http://localhost:8080/api/graphiql`
- **GraphQL Endpoint (POST):** `http://localhost:8080/api/graphql`
- **Health Check:** `http://localhost:8080/api/actuator/health`

---

## 📝 Cómo Probar GraphQL

### Opción 1: GraphiQL (Desde el Navegador)
1. Abre tu navegador
2. Ve a: `http://localhost:8080/api/graphiql`
3. Escribe tus queries en el panel izquierdo
4. Presiona el botón de "Play" para ejecutar

### Opción 2: Postman
1. Abre Postman
2. Método: **POST**
3. URL: `http://localhost:8080/api/graphql`
4. Headers: `Content-Type: application/json`
5. Body (raw JSON):
```json
{
  "query": "query { obtenerEmpresas { id nombre correo } }"
}
```

### Opción 3: cURL (Terminal)
```bash
curl -X POST http://localhost:8080/api/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "query { obtenerEmpresas { id nombre correo } }"}'
```

---

## 🔍 QUERIES (Consultas)

### 1. Obtener todas las empresas
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

### 2. Obtener empresa por ID
```graphql
query {
  obtenerEmpresaPorId(id: "123e4567-e89b-12d3-a456-426614174000") {
    id
    nombre
    correo
    rubro
  }
}
```

### 3. Obtener todas las ofertas de trabajo
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

### 4. Obtener oferta por ID
```graphql
query {
  obtenerOfertaTrabajoPorId(id: "123e4567-e89b-12d3-a456-426614174000") {
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

### 5. Obtener todas las entrevistas
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

### 6. Obtener entrevista por ID
```graphql
query {
  obtenerEntrevistaPorId(id: "123e4567-e89b-12d3-a456-426614174000") {
    id
    fecha
    duracionMin
    entrevistador
  }
}
```

### 7. Obtener todas las evaluaciones
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

### 8. Obtener todas las postulaciones
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

### 9. Obtener todas las visualizaciones de ofertas
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

---

## ✏️ MUTATIONS (Modificaciones)

### 1. Crear una empresa
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

### 2. Eliminar una empresa
```graphql
mutation {
  eliminarEmpresa(id: "123e4567-e89b-12d3-a456-426614174000")
}
```

### 3. Crear una oferta de trabajo
```graphql
mutation {
  crearOfertaTrabajo(
    titulo: "Desarrollador Full Stack"
    descripcion: "Buscamos desarrollador con experiencia en Spring Boot y React"
    salario: 5000.0
    ubicacion: "La Paz, Bolivia"
    requisitos: "Java, Spring Boot, React, PostgreSQL"
    fechaPublicacion: "2025-11-01"
    empresaId: "123e4567-e89b-12d3-a456-426614174000"
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

### 4. Crear una entrevista
```graphql
mutation {
  crearEntrevista(
    fecha: "2025-11-15"
    duracionMin: 60
    objetivosTotales: "Evaluar conocimientos técnicos y habilidades"
    objetivosCubiertos: "Java, Spring Boot"
    entrevistador: "Juan Pérez"
    postulacionId: "123e4567-e89b-12d3-a456-426614174000"
  ) {
    id
    fecha
    entrevistador
  }
}
```

### 5. Crear una evaluación
```graphql
mutation {
  crearEvaluacion(
    calificacionTecnica: 8.5
    calificacionActitud: 9.0
    calificacionGeneral: 8.75
    comentarios: "Excelente candidato, muy motivado"
    entrevistaId: "123e4567-e89b-12d3-a456-426614174000"
  ) {
    id
    calificacionTecnica
    calificacionGeneral
    comentarios
  }
}
```

### 6. Crear una postulación
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
    ofertaId: "123e4567-e89b-12d3-a456-426614174000"
  ) {
    id
    nombre
    puestoActual
    oferta {
      titulo
    }
  }
}
```

### 7. Crear una visualización de oferta
```graphql
mutation {
  crearVisualizacionOferta(
    fechaVisualizacion: "2025-11-01"
    origen: "LinkedIn"
    ofertaId: "123e4567-e89b-12d3-a456-426614174000"
  ) {
    id
    fechaVisualizacion
    origen
  }
}
```

---

## 🎯 Ejemplo Completo con Postman

**Configuración en Postman:**

1. **Method:** POST
2. **URL:** `http://localhost:8080/api/graphql`
3. **Headers:**
   - `Content-Type: application/json`
4. **Body (raw JSON):**
```json
{
  "query": "mutation { crearEmpresa(nombre: \"Mi Empresa\", correo: \"info@miempresa.com\", rubro: \"Servicios\") { id nombre correo } }"
}
```

---

## ⚠️ Notas Importantes

1. **El endpoint `/graphql` solo acepta POST**, no GET
2. **Para usar desde el navegador**, usa GraphiQL en `/graphiql`
3. **Los UUIDs** deben estar en formato válido (por ejemplo: `123e4567-e89b-12d3-a456-426614174000`)
4. **Las fechas** deben estar en formato string (por ejemplo: `"2025-11-01"`)
5. **Los campos marcados con `!`** son obligatorios

