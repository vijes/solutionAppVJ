-- BaseDatos.sql

CREATE DATABASE IF NOT EXISTS bankdb;
USE bankdb;

-- Tabla Persona (no se crea directamente si usamos herencia, pero Clientes hereda)
-- Pero el requerimiento dice "Implementar la clase persona... Debe manejar su clave primaria".
-- Si Cliente hereda de Persona, y usamos JPA @MappedSuperclass, la tabla Cliente tendrá todos los campos.
-- Si usamos @Inheritance(JOINED), tendríamos tabla Persona y Cliente.
-- Dado nuestro modelo JPA: Cliente es @Entity con @PrimaryKeyJoinColumn, pero @MappedSuperclass en Persona.
-- Un momento, @MappedSuperclass NO crea tabla. Los atributos van a la tabla hija.

-- Tabla Cliente
CREATE TABLE IF NOT EXISTS cliente (
    cliente_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    genero VARCHAR(20),
    edad INT,
    identificacion VARCHAR(20) UNIQUE,
    direccion VARCHAR(200),
    telefono VARCHAR(20),
    contrasena VARCHAR(100),
    estado BOOLEAN
);

-- Tabla Cuenta
CREATE TABLE IF NOT EXISTS cuenta (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero_cuenta VARCHAR(20) UNIQUE,
    tipo_cuenta VARCHAR(20),
    saldo_inicial DOUBLE,
    estado BOOLEAN,
    cliente_id BIGINT,
    FOREIGN KEY (cliente_id) REFERENCES cliente(cliente_id)
);

-- Tabla Movimiento
CREATE TABLE IF NOT EXISTS movimiento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha DATETIME,
    tipo_movimiento VARCHAR(50),
    valor DOUBLE,
    saldo DOUBLE,
    cuenta_id BIGINT,
    FOREIGN KEY (cuenta_id) REFERENCES cuenta(id)
);

-- Insertar Datos de Ejemplo (Casos de Uso)
INSERT INTO cliente (nombre, direccion, telefono, contrasena, estado) VALUES 
('Jose Lema', 'Otavalo sn y principal', '098254785', '1234', true),
('Marianela Montalvo', 'Amazonas y NNUU', '097548965', '5678', true),
('Juan Osorio', '13 junio y Equinoccial', '098874587', '1245', true);

INSERT INTO cuenta (numero_cuenta, tipo_cuenta, saldo_inicial, estado, cliente_id) VALUES 
('478758', 'Ahorro', 2000, true, 1),
('225487', 'Corriente', 100, true, 2),
('495878', 'Ahorros', 0, true, 3),
('496825', 'Ahorros', 540, true, 2),
('585545', 'Corriente', 1000, true, 1);

-- Movimientos
-- Retiro de 575 de cuenta 478758 (Jose Lema)
INSERT INTO movimiento (fecha, tipo_movimiento, valor, saldo, cuenta_id) VALUES (NOW(), 'Retiro', -575, 1425, 1);
-- Depósito de 600 de cuenta 225487 (Marianela)
INSERT INTO movimiento (fecha, tipo_movimiento, valor, saldo, cuenta_id) VALUES (NOW(), 'Deposito', 600, 700, 2);
-- Depósito de 150 de cuenta 495878 (Juan)
INSERT INTO movimiento (fecha, tipo_movimiento, valor, saldo, cuenta_id) VALUES (NOW(), 'Deposito', 150, 150, 3);
-- Retiro de 540 de cuenta 496825 (Marianela)
INSERT INTO movimiento (fecha, tipo_movimiento, valor, saldo, cuenta_id) VALUES (NOW(), 'Retiro', -540, 0, 4);
