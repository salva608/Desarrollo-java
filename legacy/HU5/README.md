# Historia de Usuario M5.1S5 - HU5
## Corporate Talent Hub - Persistencia Relacional y Arquitectura MVC

### 📋 Descripción del Proyecto

Este proyecto implementa la **Historia de Usuario 5 (HU5)** del sistema Corporate Talent Hub, culminando el desarrollo con la integración de persistencia de datos mediante **JDBC** y la organización del código bajo el patrón **Modelo-Vista-Controlador (MVC)**.

---

## ✅ Tasks Implementadas

### **TASK 1: Gestión de Conexiones (Legacy vs Modern)**

📁 Archivo: `src/main/java/org/riwi/util/DBConnection.java`

**Implementación:**
- ✅ Clase de utilidad para conexión JDBC a MySQL
- ✅ Comparación documentada: Java 8 (finally) vs Java 17/21 (try-with-resources)
- ✅ Explicación de prevención de Memory Leaks mediante comentarios extensos

**Características Modernas:**
```java
// Try-with-resources - Cierre automático de recursos
try (Connection conn = DBConnection.getConnection();
     PreparedStatement stmt = conn.prepareStatement(sql);
     ResultSet rs = stmt.executeQuery()) {
    // Los recursos se cierran automáticamente
    // Previene MEMORY LEAKS
}
```

**Ventajas sobre Legacy:**
- Cierre automático de Connection, PreparedStatement, ResultSet
- Prevención garantizada de fugas de memoria
- Código más limpio y mantenible
- Manejo robusto de excepciones

---

### **TASK 2: Implementación del DAO y CRUD Seguro**

📁 Archivos:
- `src/main/java/org/riwi/dao/EmpleadoDAO.java` (Interfaz)
- `src/main/java/org/riwi/dao/EmpleadoDAOImpl.java` (Implementación)
- `src/main/java/org/riwi/model/Empleado.java` (Entidad)

**CRUD Implementado:**
- ✅ **Create**: `insertar(Empleado empleado)`
- ✅ **Read**: `listarTodos()` y `buscarPorId(int id)`
- ✅ **Update**: `actualizar(Empleado empleado)`
- ✅ **Delete**: `eliminar(int id)`

**Seguridad:**
- ✅ **100% PreparedStatement** - Protección contra SQL Injection
- ✅ Validación de parámetros
- ✅ Documentación de riesgos de seguridad

**Ejemplo de Seguridad:**
```java
// ❌ INSEGURO (Inyección SQL posible)
String sql = "SELECT * FROM empleados WHERE nombre = '" + nombre + "'";

// ✅ SEGURO (PreparedStatement)
String sql = "SELECT * FROM empleados WHERE nombre = ?";
stmt.setString(1, nombre);
```

---

### **TASK 3: Estructuración MVC**

📦 **Estructura de Paquetes:**
```
org.riwi.
├── model/          # Entidades y Records
│   ├── Empleado.java
│   ├── EmpleadoReporteDTO.java (Record)
├── dao/            # Acceso a datos
│   ├── EmpleadoDAO.java
│   ├── EmpleadoDAOImpl.java
│   └── ReporteService.java
├── controller/     # Lógica de negocio
│   └── EmpleadoController.java
├── view/           # Interfaz de usuario
│   └── MenuView.java
└── util/           # Utilidades
    └── DBConnection.java
```

**Separación de Responsabilidades:**
- **Modelo**: Entidades y lógica de datos
- **Vista**: Interacción con usuario (Scanner), sin lógica de negocio
- **Controlador**: Mediador entre Vista y Modelo, aplica reglas de negocio

---

### **TASK 4: Integración de Records (Java 17+)**

📁 Archivos:
- `src/main/java/org/riwi/model/EmpleadoReporteDTO.java`
- `src/main/java/org/riwi/dao/ReporteService.java`

**Características:**
- ✅ Record `EmpleadoReporteDTO` para consultas JOIN complejas
- ✅ Record `EstadisticasDTO` para métricas consolidadas
- ✅ Text Blocks para reportes legibles
- ✅ Análisis documentado: Records vs POJOs (Java 8)

**Ventajas de Records:**
```java
// Record: 1 línea
public record EmpleadoReporteDTO(int id, String nombre, long salario) {}

// vs POJO tradicional: 40+ líneas
// (constructores, getters, equals, hashCode, toString)
```

**Beneficios:**
- Inmutabilidad por diseño (thread-safe)
- Código 90% más conciso
- Mantenimiento simplificado
- Ideal para DTOs y ResultSets

---

## 🗄️ Base de Datos

### Configuración MySQL

**Archivo:** `database.sql`

**Tablas:**
1. **departamentos**
   - id_departamento (PK)
   - nombre
   - descripcion
   - fecha_creacion

2. **empleados**
   - id_empleado (PK)
   - nombre
   - salario_base
   - bono_mensual
   - genero
   - es_activo
   - departamento_id (FK)
   - fecha_registro
   - fecha_actualizacion

**Datos de Prueba:**
- 5 departamentos
- 10 empleados de ejemplo
- Vista consolidada: `vista_empleados_completa`

---

## 🚀 Instalación y Ejecución

### Prerrequisitos

- Java 21 (JDK)
- Maven 3.8+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### Paso 1: Configurar Base de Datos

```bash
# Conectar a MySQL
mysql -u root -p

# Ejecutar script de creación
source database.sql
```

### Paso 2: Configurar Conexión

Editar `DBConnection.java` con tus credenciales:

```java
private static final String URL = "jdbc:mysql://localhost:3306/talent_hub_db";
private static final String USER = "tu_usuario";
private static final String PASSWORD = "tu_contraseña";
```

### Paso 3: Compilar y Ejecutar

```bash
# Compilar con Maven
mvn clean compile

# Ejecutar
mvn exec:java

# O desde tu IDE: Run Main.java
```

---

## 📱 Funcionalidades del Sistema

### Menú Principal

```
┌─────────────── MENÚ PRINCIPAL ───────────────┐
│  1. ➕ Registrar nuevo empleado              │
│  2. 📋 Listar todos los empleados            │
│  3. 🔍 Buscar empleado por ID                │
│  4. ✏️  Actualizar empleado                   │
│  5. 🗑️  Eliminar empleado                     │
│  6. 📊 Generar reporte consolidado           │
│  0. 🚪 Salir                                  │
└──────────────────────────────────────────────┘
```

### Operaciones CRUD

1. **Registrar**: Captura nombre, salario, bono, género, estado
2. **Listar**: Muestra todos los empleados en tabla formateada
3. **Buscar**: Encuentra empleado por ID con detalles completos
4. **Actualizar**: Modifica datos existentes con validación
5. **Eliminar**: Borra empleado con confirmación
6. **Reporte**: Genera informe consolidado con estadísticas

---

## 📊 Reportes Avanzados

### Reporte Consolidado

Utiliza **Records** y **Text Blocks** para generar reportes profesionales:

```
╔═══════════════════════════════════════════════════════════╗
║  ID  │  NOMBRE  │  SALARIO  │  BONO  │  TOTAL  │  ESTADO  ║
╠═══════════════════════════════════════════════════════════╣
║  1   │  Ana     │ $4,500,000│$450,000│$4,995,000│ ACTIVO  ║
...
╚═══════════════════════════════════════════════════════════╝
```

### Estadísticas

- Total de empleados
- Empleados activos/inactivos
- Porcentaje de actividad
- Salarios: promedio, máximo, mínimo

---

## 🔒 Seguridad Implementada

### Prevención de SQL Injection

**Todas las consultas SQL** utilizan `PreparedStatement`:

```java
// Ejemplo en EmpleadoDAOImpl
String sql = "INSERT INTO empleados (nombre, salario_base) VALUES (?, ?)";
try (PreparedStatement stmt = conn.prepareStatement(sql)) {
    stmt.setString(1, empleado.getNombre());
    stmt.setLong(2, empleado.getSalarioBase());
    stmt.executeUpdate();
}
```

### Gestión de Recursos

- Try-with-resources en todas las operaciones de BD
- Cierre automático de Connection, Statement, ResultSet
- Prevención garantizada de Memory Leaks

---

## 📚 Conceptos Clave Demostrados

### Java Moderno (17/21) vs Legacy (8)

| Característica | Java 8 (Legacy) | Java 17/21 (Modern) |
|----------------|-----------------|---------------------|
| Cierre de recursos | try-finally manual | try-with-resources automático |
| DTOs | POJOs (40+ líneas) | Records (1 línea) |
| Inmutabilidad | Requiere final + sin setters | Records inmutables por defecto |
| Strings multilínea | Concatenación/StringBuilder | Text Blocks (""") |

### Patrón MVC

```
Usuario → Vista (MenuView)
            ↓ Captura entrada
         Controlador (EmpleadoController)
            ↓ Valida y procesa
         Modelo (DAO + Entidades)
            ↓ Consulta BD
         Base de Datos (MySQL)
```

---

## 🎯 Objetivos de Aprendizaje Cumplidos

✅ **TASK 1**: Comprender evolución de gestión de recursos en Java
✅ **TASK 2**: Implementar CRUD seguro contra SQL Injection
✅ **TASK 3**: Aplicar patrón MVC con separación de responsabilidades
✅ **TASK 4**: Utilizar características modernas de Java (Records, Text Blocks)

---

## 📝 Notas Adicionales

### Migración a CRC (Proyecto Principal)

Para integrar HU5 al proyecto principal:

1. Copiar paquetes a `com.riwi.talent.*`
2. Actualizar imports
3. Integrar con funcionalidades de HU2, HU3, HU4
4. Agregar dependencia MySQL al pom.xml principal

### Mejoras Futuras

- [ ] Implementar conexión pool (HikariCP)
- [ ] Agregar JPA/Hibernate como alternativa a JDBC
- [ ] Crear API REST con Spring Boot
- [ ] Implementar autenticación y autorización
- [ ] Agregar pruebas unitarias (JUnit 5)
- [ ] Dockerizar la aplicación

---

## 👨‍💻 Autor

Proyecto desarrollado para el módulo M5.1S5 de RIWI  
Historia de Usuario: **HU5 - Persistencia Relacional y MVC**

---

## 📄 Licencia

Proyecto educativo - RIWI © 2024
