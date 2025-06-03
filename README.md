# Sistema de Turnos - Proyecto de Gestión de Citas Médicas

## 📋 Descripción del Proyecto

Sistema de gestión de turnos médicos desarrollado con **Spring Boot** y **Java**, que permite la administración eficiente de citas entre pacientes y profesionales de la salud. El sistema incluye notificaciones en tiempo real, logging avanzado y una API REST completa.

## 🚀 Características Principales

- ✅ **Gestión de Turnos**: Crear, consultar, modificar y cancelar turnos
- ✅ **Gestión de Pacientes**: Registro y administración de información de pacientes
- ✅ **Notificaciones Asíncronas**: Sistema de mensajería con RabbitMQ
- ✅ **Logging Avanzado**: Registro completo de operaciones con MongoDB
- ✅ **API REST Documentada**: Documentación automática con Swagger/OpenAPI
- ✅ **Pruebas Automatizadas**: Cobertura completa con JUnit y Mockito
- ✅ **Base de Datos Dual**: MySQL para datos principales, MongoDB para logs

## 🛠️ Tecnologías Utilizadas

### Backend
- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **Spring AMQP (RabbitMQ)**
- **Spring Data MongoDB**

### Base de Datos
- **MySQL 8.0** - Datos principales
- **MongoDB** - Sistema de logs

### Mensajería
- **RabbitMQ** - Cola de mensajes asíncrona

### Testing
- **JUnit 5**
- **Mockito**
- **Spring Boot Test**

### Documentación
- **Swagger/OpenAPI 3**
- **Spring Doc**

## 📁 Estructura del Proyecto

```
sistema-turnos/
├── src/
│   ├── main/
│   │   ├── java/com/example/sistematurno/
│   │   │   ├── controller/          # Controladores REST
│   │   │   ├── service/             # Lógica de negocio
│   │   │   ├── repository/          # Repositorios JPA/MongoDB
│   │   │   ├── model/               # Entidades y DTOs
│   │   │   ├── config/              # Configuraciones
│   │   │   └── SistemaTurnoApplication.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   └── test/
│       └── java/com/example/sistematurno/
│           ├── service/             # Tests de servicios
│           └── controller/          # Tests de controladores
├── logs/                            # Archivos de log
├── docs/                            # Documentación adicional
├── pom.xml
└── README.md
```

## ⚙️ Configuración del Entorno

### Requisitos Previos
- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+
- MongoDB 4.4+
- RabbitMQ 3.8+

### Configuración de Base de Datos MySQL
```sql
CREATE DATABASE sistema_turnos;
CREATE USER 'turnos_user'@'localhost' IDENTIFIED BY 'tu_password';
GRANT ALL PRIVILEGES ON sistema_turnos.* TO 'turnos_user'@'localhost';
FLUSH PRIVILEGES;
```

### Configuración de MongoDB
```bash
# Crear base de datos y colección para logs
mongo
use sistema_turnos_logs
db.createCollection("logs")
```

### Configuración de RabbitMQ
```bash
# Habilitar management plugin
rabbitmq-plugins enable rabbitmq_management

# Crear usuario (opcional)
rabbitmqctl add_user turnos_user password
rabbitmqctl set_user_tags turnos_user administrator
rabbitmqctl set_permissions -p / turnos_user ".*" ".*" ".*"
```

## 🔧 Instalación y Ejecución

### 1. Clonar el Repositorio
```bash
git clone https://github.com/MMarcos00/sistema-turnos.git
cd sistema-turnos
```

### 2. Configurar application.properties
```properties
# Configuración de la aplicación
spring.application.name=sistematurno

# Base de datos MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/sistema_turnos?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# RabbitMQ
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

# MongoDB para logs
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=sistema_turnos_logs

# Configuración de logging
logging.level.com.example.sistematurno=DEBUG
logging.file.name=logs/sistema-turnos.log
```

### 3. Compilar y Ejecutar
```bash
# Compilar el proyecto
mvn clean compile

# Ejecutar tests
mvn test

# Ejecutar la aplicación
mvn spring-boot:run
```

### 4. Acceder a la Aplicación
- **API REST**: http://localhost:8080/api
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs**: http://localhost:8080/api-docs
- **Health Check**: http://localhost:8080/actuator/health

## 📚 Documentación de la API

### Endpoints Principales

#### Gestión de Turnos
```http
GET    /api/turnos              # Listar todos los turnos
POST   /api/turnos              # Crear nuevo turno
GET    /api/turnos/{id}         # Obtener turno por ID
PUT    /api/turnos/{id}         # Actualizar turno
DELETE /api/turnos/{id}         # Eliminar turno
GET    /api/turnos/estado/{estado}  # Filtrar por estado
```

#### Gestión de Pacientes
```http
GET    /api/pacientes           # Listar todos los pacientes
POST   /api/pacientes           # Crear nuevo paciente
GET    /api/pacientes/{id}      # Obtener paciente por ID
PUT    /api/pacientes/{id}      # Actualizar paciente
DELETE /api/pacientes/{id}      # Eliminar paciente
```



```

## 🏗️ Arquitectura del Sistema

### Patrón de Capas
```
┌─────────────────────────────────────┐
│          Capa de Presentación       │
│            (Controllers)            │
├─────────────────────────────────────┤
│          Capa de Negocio            │
│             (Services)              │
├─────────────────────────────────────┤
│         Capa de Persistencia        │
│           (Repositories)            │
├─────────────────────────────────────┤
│          Capa de Datos              │
│        (MySQL + MongoDB)            │
└─────────────────────────────────────┘
```

### Componentes Principales

#### 1. Modelo de Datos
- **Turno**: Entidad principal que representa una cita médica
- **Paciente**: Información del paciente
- **LogEntry**: Registro de auditoría en MongoDB

#### 2. Servicios
- **TurnoService**: Lógica de negocio para gestión de turnos
- **PacienteService**: Gestión de información de pacientes
- **LogService**: Sistema de logging y auditoría
- **RabbitMQService**: Manejo de notificaciones asíncronas

#### 3. Repositorios
- **TurnoRepository**: Acceso a datos de turnos (MySQL)
- **PacienteRepository**: Acceso a datos de pacientes (MySQL)
- **LogRepository**: Acceso a logs (MongoDB)

## 🧪 Testing

### Ejecutar Pruebas
```bash
# Ejecutar todas las pruebas
mvn test

# Ejecutar pruebas con reporte de cobertura
mvn test jacoco:report

# Ejecutar solo pruebas unitarias
mvn test -Dtest="*Test"

# Ejecutar solo pruebas de integración
mvn test -Dtest="*IT"
```

### Cobertura de Pruebas
- **Servicios**: Pruebas unitarias con Mockito
- **Controladores**: Pruebas de integración con MockMvc
- **Repositorios**: Pruebas con TestContainers
- **Cobertura objetivo**: >80%

### Ejemplos de Pruebas
```java
@Test
void testCrearTurno() {
    // Given
    when(pacienteRepository.findById(1L)).thenReturn(Optional.of(paciente));
    when(turnoRepository.save(any(Turno.class))).thenReturn(turno);

    // When
    Turno resultado = turnoService.crearTurno(turno);

    // Then
    assertNotNull(resultado);
    assertEquals("PROGRAMADO", resultado.getEstado());
    verify(logService).logTurno(eq("CREAR"), any(), any());
}
```

## 📊 Logging y Monitoreo

### Sistema de Logs
- **Logs de aplicación**: Archivo rotativo en `/logs/sistema-turnos.log`
- **Logs de auditoría**: MongoDB para consultas y análisis
- **Métricas**: Actuator endpoints para monitoreo

### Niveles de Log
- **ERROR**: Errores críticos del sistema
- **WARN**: Advertencias y situaciones anómalas
- **INFO**: Operaciones importantes (CRUD, accesos)
- **DEBUG**: Información detallada para desarrollo

### Consultar Logs
```bash
# Ver logs en tiempo real
tail -f logs/sistema-turnos.log

# Buscar errores
grep "ERROR" logs/sistema-turnos.log

# Logs por fecha
grep "2024-12-15" logs/sistema-turnos.log
```

## 🔒 Seguridad

### Validaciones Implementadas
- Validación de datos de entrada con Bean Validation
- Sanitización de parámetros SQL
- Validación de fechas futuras para turnos
- Unicidad de email en pacientes

### Medidas de Seguridad
- Logs de auditoría para todas las operaciones
- Validación de integridad referencial
- Manejo seguro de excepciones
- Headers de seguridad HTTP


### Docker (Opcional)
```dockerfile
FROM openjdk:17-jre-slim
COPY target/sistema-turnos-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

### Variables de Entorno
```bash
export DB_HOST=localhost
export DB_PORT=3306
export DB_NAME=sistema_turnos
export DB_USER=root
export DB_PASSWORD=tu_password
export RABBITMQ_HOST=localhost
export MONGODB_HOST=localhost
```

## 📈 Rendimiento y Escalabilidad

### Optimizaciones Implementadas
- Índices de base de datos para consultas frecuentes
- Lazy loading en relaciones JPA
- Paginación para listados grandes
- Cache de consultas frecuentes
- Procesamiento asíncrono con RabbitMQ

### Métricas de Rendimiento
- **Consultas simples**: < 50ms
- **Operaciones CRUD**: < 100ms
- **Carga de páginas**: < 200ms
- **Procesamiento de notificaciones**: Asíncrono

## 🤝 Contribución

### Cómo Contribuir
1. Fork el repositorio
2. Crear rama feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit cambios (`git commit -am 'Agregar nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Crear Pull Request

### Estándares de Código
- Java Code Conventions
- Comentarios en español
- Tests para nueva funcionalidad
- Documentación actualizada

## 📝 Changelog

### v1.0.0 (Semana 4)
- ✅ Sistema de logging con MongoDB
- ✅ Pruebas unitarias y de integración
- ✅ Documentación API con Swagger
- ✅ Estructura de datos documentada
- ✅ README completo

### v0.3.0 (Semana 3)
- ✅ Integración con RabbitMQ
- ✅ Notificaciones asíncronas
- ✅ Manejo de colas de mensajes

### v0.2.0 (Semana 2)
- ✅ API REST completa
- ✅ CRUD de turnos y pacientes
- ✅ Validaciones de negocio

### v0.1.0 (Semana 1)
- ✅ Configuración inicial
- ✅ Modelos de datos
- ✅ Configuración de base de datos

## 📞 Contacto y Soporte

- **Desarrollador**: MMarcos00
- **GitHub**: https://github.com/MMarcos00/sistema-turnos
- **Email**: [mendezmarcosgt@gmail.com]

## 📄 Licencia

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

---

## 📚 Documentación Adicional

- [Estructuras de Datos](ESTRUCTURAS_DE_DATOS.md)
- [Guía de Instalación Detallada](docs/INSTALACION.md)
- [Manual de Usuario](docs/MANUAL_USUARIO.md)
- [Troubleshooting](docs/TROUBLESHOOTING.md)

---

⭐ **Si este proyecto te fue útil, no olvides darle una estrella en GitHub** ⭐