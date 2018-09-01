DROP SCHEMA IF EXISTS bichomon_tp1_jdbc;
CREATE SCHEMA bichomon_tp1_jdbc;

USE bichomon_tp1_jdbc;

CREATE TABLE especie (
  id int NOT NULL UNIQUE,
  nombre varchar(255) NOT NULL UNIQUE,
  altura int NOT NULL,
  peso int NOT NULL,
  energiaInicial int NOT NULL,
  tipo varchar(255) NOT NULL,
  urlFoto varchar(255) NOT NULL,
  cantidadBichos int NOT NULL,
  PRIMARY KEY (nombre)
)
ENGINE = InnoDB;