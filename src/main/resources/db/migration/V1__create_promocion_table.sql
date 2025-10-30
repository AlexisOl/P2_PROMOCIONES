-- Migración para crear la tabla promocion en MariaDB
CREATE TABLE promocion (
                           promocion_id CHAR(36) NOT NULL,
                           nombre VARCHAR(200) NOT NULL,
                           descripcion VARCHAR(500),
                           tipo VARCHAR(20) NOT NULL,
                           porcentaje_descuento DOUBLE NOT NULL,
                           fecha_inicio DATE NOT NULL,
                           fecha_fin DATE NOT NULL,
                           activa BOOLEAN NOT NULL DEFAULT TRUE,
                           cine_id CHAR(36) NOT NULL,
                           sala_id CHAR(36) NULL,
                           pelicula_id CHAR(36) NULL,
                           cliente_id CHAR(36) NULL,
                           PRIMARY KEY (promocion_id),
                           CONSTRAINT chk_tipo_promocion CHECK (tipo IN ('SALA', 'PELICULA', 'CLIENTE', 'BOLETOS', 'SNACKS', 'AMBOS')),
                           CONSTRAINT chk_porcentaje CHECK (porcentaje_descuento > 0 AND porcentaje_descuento <= 100),
                           CONSTRAINT chk_fechas CHECK (fecha_fin >= fecha_inicio)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Índices para mejorar el rendimiento
CREATE INDEX idx_promocion_cine ON promocion(cine_id);
CREATE INDEX idx_promocion_sala ON promocion(sala_id);
CREATE INDEX idx_promocion_pelicula ON promocion(pelicula_id);
CREATE INDEX idx_promocion_cliente ON promocion(cliente_id);
CREATE INDEX idx_promocion_tipo ON promocion(tipo);
CREATE INDEX idx_promocion_activa ON promocion(activa);
CREATE INDEX idx_promocion_fechas ON promocion(fecha_inicio, fecha_fin);

ALTER TABLE promocion COMMENT = 'Tabla de promociones para salas de cine';