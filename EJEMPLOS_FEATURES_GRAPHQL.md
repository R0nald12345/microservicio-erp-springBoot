# Ejemplos de Consultas GraphQL para Features

## 1. Obtener Features de Postulantes

```graphql
query ObtenerFeaturesPostulantes {
  obtenerFeaturesPostulantes {
    idPostulante
    aniosExperiencia
    nivelEducacion
    habilidades
    idiomas
    certificaciones
    puestoActual
    idOfertaTrabajo
  }
}
```

## 2. Obtener Features de Ofertas

```graphql
query ObtenerFeaturesOfertas {
  obtenerFeaturesOfertas {
    idOferta
    titulo
    salario
    ubicacion
    requisitos
    idEmpresa
  }
}
```

## 3. Obtener Features de Empresas

```graphql
query ObtenerFeaturesEmpresas {
  obtenerFeaturesEmpresas {
    idEmpresa
    rubro
  }
}
```

## 4. Consulta Combinada (todas las features en una sola consulta)

```graphql
query ObtenerTodasLasFeatures {
  postulantes: obtenerFeaturesPostulantes {
    idPostulante
    aniosExperiencia
    nivelEducacion
    habilidades
    idiomas
    certificaciones
    puestoActual
    idOfertaTrabajo
  }

  ofertas: obtenerFeaturesOfertas {
    idOferta
    titulo
    salario
    ubicacion
    requisitos
    idEmpresa
  }

  empresas: obtenerFeaturesEmpresas {
    idEmpresa
    rubro
  }
}
```

## Descripción de los Campos

### Features del Postulante:

- `idPostulante`: UUID único del postulante
- `aniosExperiencia`: Años de experiencia laboral
- `nivelEducacion`: Nivel educativo alcanzado
- `habilidades`: Habilidades técnicas y blandas
- `idiomas`: Idiomas que maneja
- `certificaciones`: Certificaciones profesionales
- `puestoActual`: Cargo actual o último empleo
- `idOfertaTrabajo`: ID de la oferta a la que postula

### Features de la Oferta:

- `idOferta`: UUID único de la oferta
- `titulo`: Título del puesto
- `salario`: Salario ofrecido
- `ubicacion`: Ubicación del trabajo
- `requisitos`: Requisitos para el puesto
- `idEmpresa`: ID de la empresa que publica la oferta

### Features de la Empresa:

- `idEmpresa`: UUID único de la empresa
- `rubro`: Sector o rubro de la empresa

## Notas de Implementación

Estas consultas permiten obtener los datos de manera separada, como solicitaste:

1. **Query 1**: Features del postulante con relación al ID de la oferta
2. **Query 2**: Features de la oferta con relación al ID de la empresa
3. **Query 3**: Features de la empresa

Cada consulta está optimizada para obtener solo los datos necesarios, facilitando su uso en análisis de datos o machine learning.
