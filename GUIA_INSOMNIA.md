# 🧪 Guía Paso a Paso: Probar GraphQL en Insomnia

## 📋 Configuración Inicial

### Paso 1: Crear una Nueva Solicitud GraphQL
1. Abre Insomnia
2. Clic en **"New Request"** o presiona `Ctrl+N` (Windows) / `Cmd+N` (Mac)
3. Selecciona **"GraphQL Request"**
4. O si prefieres usar HTTP normal, selecciona **"HTTP Request"** y configura manualmente

---

## 🔧 Configuración de la Solicitud

### Opción A: Usar GraphQL Request (Recomendado)

1. **URL:** `http://localhost:8080/api/graphql`
2. **Method:** Ya está configurado como POST automáticamente
3. **Body:** Selecciona "GraphQL" en el dropdown del body

### Opción B: Usar HTTP Request (Alternativa)

1. **Method:** POST
2. **URL:** `http://localhost:8080/api/graphql`
3. **Headers:**
   - `Content-Type: application/json`
4. **Body:** Selecciona "JSON" y usa el formato JSON

---

## 🔍 QUERIES (Consultas) - Paso a Paso

### Query 1: Obtener Todas las Empresas

**Si usas GraphQL Request:**
1. En el panel de la izquierda (schema), busca "obtenerEmpresas"
2. O escribe directamente en el editor GraphQL:

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

**Si usas HTTP Request:**
- Body (JSON):
```json
{
  "query": "query { obtenerEmpresas { id nombre correo rubro } }"
}
```

**Resultado esperado:**
```json
{
  "data": {
    "obtenerEmpresas": [
      {
        "id": "uuid-aqui",
        "nombre": "Nombre Empresa",
        "correo": "correo@empresa.com",
        "rubro": "Tecnología"
      }
    ]
  }
}
```

---

### Query 2: Obtener Empresa por ID

**GraphQL:**
```graphql
query {
  obtenerEmpresaPorId(id: "REEMPLAZA_CON_UN_UUID_DE_TU_DB") {
    id
    nombre
    correo
    rubro
  }
}
```

**HTTP (JSON):**
```json
{
  "query": "query { obtenerEmpresaPorId(id: \"REEMPLAZA_CON_UN_UUID_DE_TU_DB\") { id nombre correo rubro } }"
}
```

**💡 Tip:** Primero ejecuta `obtenerEmpresas` para obtener un ID real.

---

### Query 3: Obtener Todas las Ofertas de Trabajo

**GraphQL:**
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

**HTTP (JSON):**
```json
{
  "query": "query { obtenerOfertasTrabajo { id titulo descripcion salario ubicacion empresa { nombre } } }"
}
```

---

### Query 4: Obtener Oferta por ID

**GraphQL:**
```graphql
query {
  obtenerOfertaTrabajoPorId(id: "REEMPLAZA_CON_UN_UUID_DE_TU_DB") {
    id
    titulo
    descripcion
    salario
    empresa {
      nombre
      correo
    }
  }
}
```

---

### Query 5: Obtener Todas las Entrevistas

**GraphQL:**
```graphql
query {
  obtenerEntrevistas {
    id
    fecha
    duracionMin
    entrevistador
    postulacion {
      id
      nombre
      oferta {
        titulo
      }
    }
  }
}
```

---

### Query 6: Obtener Entrevista por ID

**GraphQL:**
```graphql
query {
  obtenerEntrevistaPorId(id: "REEMPLAZA_CON_UN_UUID_DE_TU_DB") {
    id
    fecha
    duracionMin
    entrevistador
    postulacion {
      nombre
    }
  }
}
```

---

### Query 7: Obtener Todas las Evaluaciones

**GraphQL:**
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

---

### Query 8: Obtener Todas las Postulaciones

**GraphQL:**
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

---

### Query 9: Obtener Todas las Visualizaciones

**GraphQL:**
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

## ✏️ MUTATIONS (Modificaciones) - Paso a Paso

### Mutation 1: Crear una Empresa

**GraphQL:**
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

**HTTP (JSON):**
```json
{
  "query": "mutation { crearEmpresa(nombre: \"Tech Solutions S.A.\", correo: \"contacto@techsolutions.com\", rubro: \"Tecnología\") { id nombre correo rubro } }"
}
```

**Resultado esperado:**
```json
{
  "data": {
    "crearEmpresa": {
      "id": "nuevo-uuid-generado",
      "nombre": "Tech Solutions S.A.",
      "correo": "contacto@techsolutions.com",
      "rubro": "Tecnología"
    }
  }
}
```

**📝 Nota:** Guarda el `id` que te devuelve para usarlo en otras queries/mutations.

---

### Mutation 2: Crear una Oferta de Trabajo

**⚠️ IMPORTANTE:** Necesitas un `empresaId` válido. Primero ejecuta `obtenerEmpresas` para obtener un ID.

**GraphQL:**
```graphql
mutation {
  crearOfertaTrabajo(
    titulo: "Desarrollador Full Stack"
    descripcion: "Buscamos desarrollador con experiencia en Spring Boot y React"
    salario: 5000.0
    ubicacion: "La Paz, Bolivia"
    requisitos: "Java, Spring Boot, React, PostgreSQL"
    fechaPublicacion: "2025-11-01"
    empresaId: "REEMPLAZA_CON_UUID_EMPRESA"
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

**HTTP (JSON):**
```json
{
  "query": "mutation { crearOfertaTrabajo(titulo: \"Desarrollador Full Stack\", descripcion: \"Buscamos desarrollador...\", salario: 5000.0, ubicacion: \"La Paz, Bolivia\", requisitos: \"Java, Spring Boot...\", fechaPublicacion: \"2025-11-01\", empresaId: \"REEMPLAZA_CON_UUID_EMPRESA\") { id titulo empresa { nombre } } }"
}
```

---

### Mutation 3: Crear una Entrevista

**⚠️ IMPORTANTE:** Necesitas un `postulacionId` válido.

**GraphQL:**
```graphql
mutation {
  crearEntrevista(
    fecha: "2025-11-15"
    duracionMin: 60
    objetivosTotales: "Evaluar conocimientos técnicos y habilidades"
    objetivosCubiertos: "Java, Spring Boot"
    entrevistador: "Juan Pérez"
    postulacionId: "REEMPLAZA_CON_UUID_POSTULACION"
  ) {
    id
    fecha
    entrevistador
    postulacion {
      nombre
    }
  }
}
```

---

### Mutation 4: Crear una Evaluación

**⚠️ IMPORTANTE:** Necesitas un `entrevistaId` válido.

**GraphQL:**
```graphql
mutation {
  crearEvaluacion(
    calificacionTecnica: 8.5
    calificacionActitud: 9.0
    calificacionGeneral: 8.75
    comentarios: "Excelente candidato, muy motivado"
    entrevistaId: "REEMPLAZA_CON_UUID_ENTREVISTA"
  ) {
    id
    calificacionTecnica
    calificacionActitud
    calificacionGeneral
    comentarios
  }
}
```

---

### Mutation 5: Crear una Postulación

**⚠️ IMPORTANTE:** Necesitas un `ofertaId` válido (de una oferta de trabajo).

**GraphQL:**
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
    ofertaId: "REEMPLAZA_CON_UUID_OFERTA"
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

---

### Mutation 6: Crear una Visualización de Oferta

**GraphQL:**
```graphql
mutation {
  crearVisualizacionOferta(
    fechaVisualizacion: "2025-11-01"
    origen: "LinkedIn"
    ofertaId: "REEMPLAZA_CON_UUID_OFERTA"
  ) {
    id
    fechaVisualizacion
    origen
    oferta {
      titulo
    }
  }
}
```

---

### Mutation 7: Eliminar una Empresa

**GraphQL:**
```graphql
mutation {
  eliminarEmpresa(id: "REEMPLAZA_CON_UUID_EMPRESA")
}
```

**Resultado esperado:**
```json
{
  "data": {
    "eliminarEmpresa": "Empresa eliminada correctamente"
  }
}
```

---

### Mutation 8: Eliminar una Oferta de Trabajo

**GraphQL:**
```graphql
mutation {
  eliminarOfertaTrabajo(id: "REEMPLAZA_CON_UUID_OFERTA")
}
```

---

### Mutation 9: Eliminar una Entrevista

**GraphQL:**
```graphql
mutation {
  eliminarEntrevista(id: "REEMPLAZA_CON_UUID_ENTREVISTA")
}
```

---

### Mutation 10: Eliminar una Evaluación

**GraphQL:**
```graphql
mutation {
  eliminarEvaluacion(id: "REEMPLAZA_CON_UUID_EVALUACION")
}
```

---

### Mutation 11: Eliminar una Postulación

**GraphQL:**
```graphql
mutation {
  eliminarPostulacion(id: "REEMPLAZA_CON_UUID_POSTULACION")
}
```

---

### Mutation 12: Eliminar una Visualización

**GraphQL:**
```graphql
mutation {
  eliminarVisualizacionOferta(id: "REEMPLAZA_CON_UUID_VISUALIZACION")
}
```

---

## 🎯 Flujo Recomendado de Pruebas

### Orden de Pruebas Recomendado:

1. **Primero:** `obtenerEmpresas` - Para ver qué empresas tienes
2. **Si no hay empresas:** `crearEmpresa` - Crear una nueva
3. **Segundo:** `obtenerOfertasTrabajo` - Ver ofertas existentes
4. **Si no hay ofertas:** `crearOfertaTrabajo` (necesitas un `empresaId`)
5. **Tercero:** `obtenerPostulaciones` - Ver postulaciones
6. **Si no hay postulaciones:** `crearPostulacion` (necesitas un `ofertaId`)
7. **Cuarto:** `obtenerEntrevistas` - Ver entrevistas
8. **Si no hay entrevistas:** `crearEntrevista` (necesitas un `postulacionId`)
9. **Quinto:** `obtenerEvaluaciones` - Ver evaluaciones
10. **Si no hay evaluaciones:** `crearEvaluacion` (necesitas un `entrevistaId`)

---

## 🔑 Variables en Insomnia

Puedes usar variables para reutilizar IDs:

1. Ve a **"Manage Environments"** (Ctrl+E)
2. Crea variables como:
   - `empresa_id`
   - `oferta_id`
   - `postulacion_id`
3. Úsalas en tus queries:
```graphql
query {
  obtenerEmpresaPorId(id: $empresa_id) {
    nombre
  }
}
```

---

## ✅ Verificación de Errores

Si recibes un error, revisa:
1. **Que el servidor esté corriendo** en `http://localhost:8080`
2. **Que los UUIDs sean válidos** (formato: `123e4567-e89b-12d3-a456-426614174000`)
3. **Que los campos obligatorios estén presentes** (marcados con `!` en el schema)
4. **Que las relaciones existan** (ej: no crear una oferta con un `empresaId` que no existe)

---

## 📸 Screenshots de Configuración

**En Insomnia GraphQL Request:**
- URL: `http://localhost:8080/api/graphql`
- Method: POST (automático)
- Body type: GraphQL
- Escribe tu query en el editor GraphQL

**En Insomnia HTTP Request:**
- Method: POST
- URL: `http://localhost:8080/api/graphql`
- Header: `Content-Type: application/json`
- Body type: JSON
- Usa el formato JSON mostrado arriba

---

¡Listo! Ahora puedes probar todos tus endpoints GraphQL en Insomnia. 🚀

