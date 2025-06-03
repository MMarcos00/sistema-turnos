# Documentación de Estructuras de Datos - Sistema de Turnos

## 1. Estructura de Datos Principal

### 1.1 Entidad Turno
```java
@Entity
@Table(name = "turnos")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;
    
    @Column(name = "fecha_hora")
    private LocalDateTime fechaHora;
    
    @Column(name = "estado")
    private String estado; // PROGRAMADO, CONFIRMADO, CANCELADO, COMPLETADO
    
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "observaciones")
    private String observaciones;
}
```

**Impacto en el Sistema:**
- **Rendimiento**: Uso de `FetchType.LAZY` para optimizar consultas y evitar cargar datos innecesarios del paciente
- **Escalabilidad**: Índices en `fecha_hora` y `estado` permiten búsquedas eficientes
- **Integridad**: Relación `@ManyToOne` garantiza consistencia referencial
- **Flexibilidad**: Campo `estado` permite gestión del ciclo de vida del turno

### 1.2 Entidad Paciente
```java
@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", nullable = false)
    private String nombre;
    
    @Column(name = "email", unique = true)
    private String email;
    
    @Column(name = "telefono")
    private String telefono;
    
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;
    
    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL)
    private List<Turno> turnos = new ArrayList<>();
}
```

**Impacto en el Sistema:**
- **Integridad**: Constraint `unique` en email previene duplicados
- **Cascada**: `CascadeType.ALL` automatiza la gestión de turnos relacionados
- **Memoria**: Lista inicializada evita `NullPointerException`
- **Búsquedas**: Índices en email y nombre mejoran performance de consultas

### 1.3 Entidad LogEntry (MongoDB)
```java
@Document(collection = "logs")
public class LogEntry {
    @Id
    private String id;
    private String nivel;           // INFO, ERROR, DEBUG, WARN
    private String mensaje;
    private String modulo;          // TURNOS, PACIENTES, ACCESO, RABBITMQ
    private String usuario;
    private LocalDateTime timestamp;
    private String detalles;
}
```

**Impacto en el Sistema:**
- **Auditoría**: Trazabilidad completa de operaciones del sistema
- **Rendimiento**: MongoDB optimizado para escrituras masivas de logs
- **Análisis**: Estructura permite consultas complejas por módulo, usuario, fecha
- **Escalabilidad**: Esquema flexible permite agregar campos sin migración

## 2. Estructuras de Datos en Memoria

### 2.1 DTOs (Data Transfer Objects)
```java
public class TurnoDTO {
    private Long id;
    private String nombrePaciente;
    private String emailPaciente;
    private LocalDateTime fechaHora;
    private String estado;
    private String descripcion;
}
```

**Impacto en el Sistema:**
- **Seguridad**: Expone solo datos necesarios al cliente
- **Performance**: Reduce transferencia de datos innecesarios
- **Mantenibilidad**: Desacopla estructura interna de API pública
- **Versionado**: Facilita evolución de API sin romper compatibilidad

### 2.2 Colas RabbitMQ
```java
// Configuración de colas
@RabbitListener(queues = "turno.notificaciones")
public void procesarNotificacion(TurnoNotificacionDTO mensaje) {
    // Procesamiento asíncrono
}
```

**Impacto en el Sistema:**
- **Asincronía**: Desacopla notificaciones del flujo principal
- **Confiabilidad**: Persistencia de mensajes garantiza entrega
- **Escalabilidad**: Permite procesamiento distribuido
- **Tolerancia a fallos**: Reintento automático de mensajes fallidos

## 3. Índices y Optimizaciones de Base de Datos

### 3.1 Índices Recomendados
```sql
-- Índices para optimizar consultas frecuentes
CREATE INDEX idx_turnos_fecha_hora ON turnos(fecha_hora);
CREATE INDEX idx_turnos_estado ON turnos(estado);
CREATE INDEX idx_turnos_paciente_id ON turnos(paciente_id);
CREATE INDEX idx_pacientes_email ON pacientes(email);
CREATE INDEX idx_logs_timestamp ON logs(timestamp);
CREATE INDEX idx_logs_modulo_nivel ON logs(modulo, nivel);
```

**Impacto en el Sistema:**
- **Consultas por fecha**: Búsqueda de turnos en rangos temporales O(log n)
- **Filtros por estado**: Listado de turnos activos/cancelados optimizado
- **Joins**: Relación turno-paciente más eficiente
- **Logs**: Búsquedas de auditoría y monitoreo aceleradas

### 3.2 Particionamiento de Logs
```sql
-- Particionamiento por fecha para logs históricos
CREATE TABLE logs_2024_01 PARTITION OF logs 
FOR VALUES FROM ('2024-01-01') TO ('2024-02-01');
```

**Impacto en el Sistema:**
- **Rendimiento**: Consultas solo escanean particiones relevantes
- **Mantenimiento**: Eliminación eficiente de logs antiguos
- **Escalabilidad**: Distribución de datos en múltiples archivos

## 4. Estructuras de Cache

### 4.1 Cache de Turnos Frecuentes
```java
@Cacheable(value = "turnos", key = "#fechaInicio + '_' + #fechaFin")
public List<Turno> obtenerTurnosPorRango(LocalDate fechaInicio, LocalDate fechaFin) {
    return turnoRepository.findByFechaHoraBetween(fechaInicio, fechaFin);
}
```

**Impacto en el Sistema:**
- **Latencia**: Reducción significativa en consultas repetitivas
- **Carga DB**: Menor presión sobre base de datos principal
- **Experiencia Usuario**: Respuestas más rápidas en pantallas frecuentes

## 5. Análisis de Complejidad y Rendimiento

### 5.1 Operaciones CRUD
| Operación | Complejidad Sin Índice | Complejidad Con Índice | Impacto |
|-----------|----------------------|----------------------|---------|
| Buscar por ID | O(n) | O(log n) | Alto |
| Buscar por fecha | O(n) | O(log n) | Crítico |
| Buscar por estado | O(n) | O(log n) | Medio |
| Insertar | O(1) | O(log n) | Bajo |
| Actualizar | O(n) | O(log n) | Medio |

### 5.2 Uso de Memoria
```java
// Optimización de consultas con paginación
@Query("SELECT t FROM Turno t WHERE t.fechaHora BETWEEN :inicio AND :fin")
Page<Turno> findTurnosPaginados(@Param("inicio") LocalDateTime inicio, 
                               @Param("fin") LocalDateTime fin, 
                               Pageable pageable);
```

**Impacto en el Sistema:**
- **Memoria**: Previene carga masiva de registros en memoria
- **Escalabilidad**: Manejo eficiente de grandes volúmenes de datos
- **UX**: Carga progresiva mejora experiencia de usuario

## 6. Consideraciones de Seguridad en Estructuras

### 6.1 Validaciones de Datos
```java
@Entity
public class Turno {
    @NotNull(message = "La fecha es obligatoria")
    @Future(message = "La fecha debe ser futura")
    private LocalDateTime fechaHora;
    
    @Pattern(regexp = "PROGRAMADO|CONFIRMADO|CANCELADO|COMPLETADO")
    private String estado;
}
```

**Impacto en el Sistema:**
- **Integridad**: Previene datos inconsistentes en base de datos
- **Seguridad**: Validación temprana evita inyecciones maliciosas
- **Mantenibilidad**: Reglas de negocio centralizadas en entidades

## 7. Métricas de Impacto

### 7.1 Rendimiento Estimado
- **Consulta simple por ID**: < 5ms
- **Búsqueda con filtros indexados**: < 50ms
- **Operaciones de escritura**: < 10ms
- **Consultas complejas con joins**: < 100ms

### 7.2 Escalabilidad
- **Turnos concurrentes**: Hasta 1000 operaciones/segundo
- **Logs por día**: Hasta 100,000 entradas
- **Usuarios simultáneos**: Hasta 500 conexiones activas
- **Crecimiento de datos**: 20% anual sostenible

## 8. Recomendaciones de Optimización

### 8.1 Corto Plazo
1. Implementar cache Redis para consultas frecuentes
2. Añadir índices compuestos para consultas complejas
3. Optimizar queries N+1 con fetch joins

### 8.2 Largo Plazo
1. Considerar sharding horizontal para crecimiento masivo
2. Implementar CQRS para separar lecturas y escrituras
3. Migrar logs históricos a almacén de datos analítico

## 9. Conclusiones

La estructura de datos implementada proporciona:
- **Flexibilidad** para evolución futura del sistema
- **Rendimiento** optimizado para operaciones críticas
- **Escalabilidad** para crecimiento sostenido
- **Mantenibilidad** a través de diseño limpio y documentado

El impacto positivo se refleja en tiempos de respuesta rápidos, facilidad de mantenimiento y capacidad de crecimiento sin reestructuración completa del sistema.