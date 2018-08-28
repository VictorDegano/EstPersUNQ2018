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

INSERT INTO especie (id, nombre, altura, peso, energiaInicial, tipo, urlFoto, cantidadBichos)
            VALUES  (1, 'Rojomon', 180, 75, 100, 'FUEGO', '/image/rojomon.jpg', 0),
                    (2, 'Amarillomon', 170, 69, 300, 'ELECTRICIDAD', '/image/amarillomon.png', 0),
                    (3, 'Verdemon', 150, 55, 5000, 'PLANTA', '/image/verdemon.jpg', 0),
                    (4, 'Tierramon', 1050, 99, 5000, 'TIERRA', '/image/tierramon.jpg', 0),
                    (5, 'Fantasmon', 1050, 99, 5000, 'AIRE', '/image/fantasmon.jpg', 0),
                    (6, 'Vampiron', 1050, 99, 5000, 'AIRE', '/image/vampiromon.jpg', 0),
                    (7, 'Fortmon', 1050, 99, 5000, 'AIRE', '/image/fortmon.png', 0),
                    (8, 'Dientemon', 1050, 99, 5000, 'AGUA', '/image/dientmon.jpg', 0);