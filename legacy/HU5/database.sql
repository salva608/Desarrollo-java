-- ============================================================================
-- HISTORIA DE USUARIO M5.1S5 - Script de Base de Datos
-- Corporate Talent Hub - Persistencia relacional
-- ============================================================================

-- Crear base de datos
DROP DATABASE IF EXISTS talent_hub_db;
CREATE DATABASE talent_hub_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE talent_hub_db;

-- ============================================================================
-- Tabla: departamentos
-- ============================================================================
CREATE TABLE departamentos (
    id_departamento INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_nombre (nombre)
) ENGINE=InnoDB;

-- ============================================================================
-- Tabla: empleados
-- ============================================================================
CREATE TABLE empleados (
    id_empleado INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    salario_base BIGINT NOT NULL CHECK (salario_base >= 0),
    bono_mensual FLOAT DEFAULT 0.0,
    genero CHAR(1) NOT NULL CHECK (genero IN ('M', 'F', 'O')),
    es_activo BOOLEAN DEFAULT true,
    departamento_id INT,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (departamento_id) REFERENCES departamentos(id_departamento)
        ON DELETE SET NULL
        ON UPDATE CASCADE,
    
    INDEX idx_nombre (nombre),
    INDEX idx_salario (salario_base),
    INDEX idx_activo (es_activo)
) ENGINE=InnoDB;

-- ============================================================================
-- Datos de ejemplo para departamentos
-- ============================================================================
INSERT INTO departamentos (nombre, descripcion) VALUES
('Tecnología', 'Departamento de desarrollo de software y sistemas'),
('Recursos Humanos', 'Gestión del talento y bienestar laboral'),
('Finanzas', 'Contabilidad y análisis financiero'),
('Marketing', 'Estrategias de mercado y comunicación'),
('Operaciones', 'Logística y procesos operativos');

-- ============================================================================
-- Datos de ejemplo para empleados
-- ============================================================================
INSERT INTO empleados (nombre, salario_base, bono_mensual, genero, es_activo, departamento_id) VALUES
('Ana García Martínez', 4500000, 450000, 'F', true, 1),
('Carlos Rodríguez López', 3800000, 380000, 'M', true, 1),
('María Fernández Gómez', 5200000, 520000, 'F', true, 2),
('Juan Pérez Sánchez', 4000000, 400000, 'M', true, 3),
('Laura Martínez Torres', 3500000, 350000, 'F', false, 4),
('Pedro González Ruiz', 4800000, 480000, 'M', true, 1),
('Carmen López Díaz', 3200000, 320000, 'F', true, 5),
('Miguel Sánchez Moreno', 5500000, 550000, 'M', true, 3),
('Isabel Ramírez Castro', 3900000, 390000, 'F', false, 2),
('Francisco Torres Vega', 4200000, 420000, 'M', true, 4);

-- ============================================================================
-- Vistas útiles
-- ============================================================================

-- Vista: Empleados con información completa
CREATE VIEW vista_empleados_completa AS
SELECT 
    e.id_empleado,
    e.nombre,
    e.salario_base,
    e.bono_mensual,
    (e.salario_base + (e.bono_mensual * 1.10)) as salario_total,
    e.genero,
    e.es_activo,
    COALESCE(d.nombre, 'Sin departamento') as departamento,
    e.fecha_registro,
    e.fecha_actualizacion
FROM empleados e
LEFT JOIN departamentos d ON e.departamento_id = d.id_departamento;

-- ============================================================================
-- Verificación de datos
-- ============================================================================
SELECT '=== VERIFICACIÓN DE DEPARTAMENTOS ===' as '';
SELECT * FROM departamentos;

SELECT '=== VERIFICACIÓN DE EMPLEADOS ===' as '';
SELECT * FROM empleados;

SELECT '=== VISTA COMPLETA DE EMPLEADOS ===' as '';
SELECT * FROM vista_empleados_completa;

SELECT '=== ESTADÍSTICAS ===' as '';
SELECT 
    COUNT(*) as total_empleados,
    SUM(CASE WHEN es_activo = true THEN 1 ELSE 0 END) as activos,
    SUM(CASE WHEN es_activo = false THEN 1 ELSE 0 END) as inactivos,
    AVG(salario_base) as salario_promedio,
    MAX(salario_base) as salario_maximo,
    MIN(salario_base) as salario_minimo
FROM empleados;
