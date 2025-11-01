# Service ERP - Spring Boot GraphQL User Management

Microservicio de gestión de usuarios construido con Spring Boot y GraphQL.

## 🏗️ Arquitectura

- **Framework:** Spring Boot 3.5.6
- **Java:** 17
- **GraphQL:** Spring GraphQL
- **Base de datos:** H2 (en memoria)
- **Puerto:** 8080
- **Tipo:** User Management & ERP

## 📁 Estructura del Proyecto

```
service_erp/
├── src/main/java/com/example/service_erp/
│   ├── ServiceErpApplication.java      # Aplicación principal
│   ├── config/
│   │   ├── CorsConfig.java            # Configuración CORS
│   │   └── DataInitializer.java       # Datos de prueba
│   ├── controller/
│   │   ├── HealthController.java      # Endpoints REST básicos
│   │   └── UserController.java        # API REST para usuarios
│   ├── model/
│   │   └── User.java                  # Entidad Usuario
│   ├── repository/
│   │   └── UserRepository.java        # Repositorio JPA
│   ├── resolver/
│   │   ├── Query.java                 # Resolvers GraphQL Query
│   │   └── Mutation.java              # Resolvers GraphQL Mutation
│   └── service/
│       └── UserService.java           # Lógica de negocio
├── src/main/resources/
│   ├── application.yml                # Configuración de la aplicación
│   └── schema.graphqls               # Esquema GraphQL
├── pom.xml                           # Dependencias Maven
├── Dockerfile                        # Para containerización
└── README.md
```

## 🚀 Inicio Rápido

### Desarrollo Local

1. **Compilar el proyecto:**

   ```bash
   ./mvnw clean compile
   ```

2. **Ejecutar el servidor:**

   ```bash
   ./mvnw spring-boot:run
   ```

3. **Acceder a los endpoints:**
   - **GraphiQL:** http://localhost:8080/api/graphiql
   - **GraphQL:** http://localhost:8080/api/graphql
   - **Health Check:** http://localhost:8080/api/health
   - **H2 Console:** http://localhost:8080/api/h2-console
   - **API Info:** http://localhost:8080/api/

### Docker

1. **Construir la imagen:**

   ```bash
   docker build -t service_erp .
   ```

2. **Ejecutar el contenedor:**
   ```bash
   docker run -p 8080:8080 service_erp
   ```

## 📊 API GraphQL

### Queries Disponibles

#### Obtener Todos los Usuarios

```graphql
query {
  users {
    id
    name
    email
    department
    position
    active
    createdAt
    updatedAt
  }
}
```

#### Obtener Usuario por ID

```graphql
query {
  user(id: 1) {
    id
    name
    email
    department
    position
    active
  }
}
```

#### Obtener Usuarios por Departamento

```graphql
query {
  usersByDepartment(department: "Desarrollo") {
    id
    name
    email
    position
  }
}
```

#### Obtener Usuarios Activos

```graphql
query {
  activeUsers {
    id
    name
    email
    department
  }
}
```

#### Health Check

```graphql
query {
  health
}
```

### Mutations Disponibles

#### Crear Usuario

```graphql
mutation {
  createUser(
    input: { name: "Nuevo Usuario", email: "nuevo@company.com", department: "IT", position: "Developer", active: true }
  ) {
    id
    name
    email
    department
    position
    active
    createdAt
  }
}
```

#### Actualizar Usuario

```graphql
mutation {
  updateUser(id: 1, input: { name: "Nombre Actualizado", department: "Nuevo Departamento" }) {
    id
    name
    email
    department
    updatedAt
  }
}
```

#### Eliminar Usuario (Soft Delete)

```graphql
mutation {
  deleteUser(id: 1)
}
```

#### Cambiar Estado del Usuario

```graphql
mutation {
  toggleUserStatus(id: 1) {
    id
    name
    active
    updatedAt
  }
}
```

## 🔧 Configuración

### Base de Datos H2

- **URL:** `jdbc:h2:mem:testdb`
- **Usuario:** `sa`
- **Contraseña:** `password`
- **Console:** http://localhost:8080/api/h2-console

### Variables de Entorno

```bash
# Puerto del servidor
SERVER_PORT=8080

# Perfil de Spring
SPRING_PROFILES_ACTIVE=development

# Configuración de base de datos
SPRING_DATASOURCE_URL=jdbc:h2:mem:testdb
SPRING_DATASOURCE_USERNAME=sa
SPRING_DATASOURCE_PASSWORD=password
```

## 📈 Datos de Ejemplo

El servicio incluye usuarios de prueba:

1. **Juan Pérez** - Desarrollo (Senior Developer)
2. **María García** - Recursos Humanos (HR Manager)
3. **Carlos López** - Desarrollo (Frontend Developer)
4. **Ana Martínez** - Marketing (Marketing Specialist) - Inactivo
5. **David Rodríguez** - Finanzas (Financial Analyst)

## 🧪 Pruebas

### Usar cURL

```bash
# Health check
curl http://localhost:8080/api/health

# GraphQL query
curl -X POST http://localhost:8080/api/graphql \
  -H "Content-Type: application/json" \
  -d '{"query": "{ users { id name email } }"}'

# REST API
curl http://localhost:8080/api/users
```

### Usar GraphiQL

Visita http://localhost:8080/api/graphiql para usar el explorador GraphQL integrado.

## 🔗 API REST (Alternativa)

Además de GraphQL, el servicio expone endpoints REST:

- `GET /api/users` - Obtener todos los usuarios
- `GET /api/users/{id}` - Obtener usuario por ID
- `GET /api/users/department/{department}` - Usuarios por departamento
- `GET /api/users/active` - Usuarios activos
- `POST /api/users` - Crear usuario
- `PUT /api/users/{id}` - Actualizar usuario
- `DELETE /api/users/{id}` - Eliminar usuario
- `PATCH /api/users/{id}/toggle-status` - Cambiar estado
- `GET /api/users/stats` - Estadísticas

## 🔗 Integración con Gateway

Este servicio está diseñado para integrarse con el Gateway GraphQL:

- **URL interna:** http://service_erp:8080 (Docker)
- **URL desarrollo:** http://localhost:8080
- **Endpoint GraphQL:** /api/graphql
- **Health check:** /api/health

## 🚨 Logging

El servicio incluye logging detallado:

- Operaciones CRUD en usuarios
- Queries y mutations GraphQL
- Errores y excepciones
- Métricas de performance

## 📦 Dependencias Principales

- `spring-boot-starter-web` - Framework web
- `spring-boot-starter-graphql` - Soporte GraphQL
- `spring-boot-starter-data-jpa` - Persistencia de datos
- `spring-boot-starter-validation` - Validaciones
- `spring-boot-starter-actuator` - Métricas y health checks
- `h2` - Base de datos en memoria
