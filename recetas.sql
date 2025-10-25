CREATE DATABASE Recetas;

USE Recetas;

-- Tabla Departamento
CREATE TABLE Departamento (
                              codigo VARCHAR(10) NOT NULL,
                              nombre VARCHAR(30) NOT NULL,
                              PRIMARY KEY (codigo)
);

-- Tabla Usuario
CREATE TABLE Usuario (
                         id VARCHAR(10) NOT NULL,
                         nombre VARCHAR(50) NOT NULL,
                         clave VARCHAR(50) NOT NULL,
                         departamento VARCHAR(10) NOT NULL,
                         PRIMARY KEY (id),
                         FOREIGN KEY (departamento) REFERENCES Departamento(codigo)
);

-- Tabla Medico
CREATE TABLE Medico (
                        id VARCHAR(10) NOT NULL,
                        nombre VARCHAR(50) NOT NULL,
                        especialidad VARCHAR(50) NOT NULL,
                        departamento VARCHAR(10) NOT NULL,
                        PRIMARY KEY (id),
                        FOREIGN KEY (departamento) REFERENCES Departamento(codigo),
                        FOREIGN KEY (id) REFERENCES Usuario(id)
);

-- Tabla Farmaceutico
CREATE TABLE Farmaceutico (
                              id VARCHAR(10) NOT NULL,
                              nombre VARCHAR(50) NOT NULL,
                              departamento VARCHAR(10) NOT NULL,
                              PRIMARY KEY (id),
                              FOREIGN KEY (departamento) REFERENCES Departamento(codigo),
                              FOREIGN KEY (id) REFERENCES Usuario(id)
);

-- Tabla Administrador
CREATE TABLE Administrador (
                               id VARCHAR(10) NOT NULL,
                               nombre VARCHAR(50) NOT NULL,
                               departamento VARCHAR(10) NOT NULL,
                               PRIMARY KEY (id),
                               FOREIGN KEY (departamento) REFERENCES Departamento(codigo),
                               FOREIGN KEY (id) REFERENCES Usuario(id)
);

-- Tabla Paciente
CREATE TABLE Paciente (
                          id VARCHAR(10) NOT NULL,
                          nombre VARCHAR(50) NOT NULL,
                          fechaNacimiento DATE,
                          telefono VARCHAR(20),
                          PRIMARY KEY (id)
);

-- Tabla Medicamento
CREATE TABLE Medicamento (
                             codigo VARCHAR(10) NOT NULL,
                             nombre VARCHAR(100) NOT NULL,
                             presentacion VARCHAR(100),
                             PRIMARY KEY (codigo)
);

-- Tabla Receta
CREATE TABLE Receta (
                        id INT AUTO_INCREMENT,
                        medicoId VARCHAR(10) NOT NULL,
                        pacienteId VARCHAR(10) NOT NULL,
                        fechaConfeccion DATE NOT NULL,
                        fechaRetiro DATE,
                        estado VARCHAR(20) NOT NULL,
                        PRIMARY KEY (id),
                        FOREIGN KEY (medicoId) REFERENCES Medico(id),
                        FOREIGN KEY (pacienteId) REFERENCES Paciente(id)
);

CREATE TABLE MedicamentoDetalle (
                                    id INT AUTO_INCREMENT,
                                    recetaId INT NOT NULL,
                                    medicamentoCodigo VARCHAR(10) NOT NULL,
                                    cantidad INT NOT NULL,
                                    indicaciones TEXT,
                                    dias INT NOT NULL,
                                    PRIMARY KEY (id),
                                    FOREIGN KEY (recetaId) REFERENCES Receta(id) ON DELETE CASCADE,
                                    FOREIGN KEY (medicamentoCodigo) REFERENCES Medicamento(codigo)
);

-- Tabla Mensaje
CREATE TABLE Mensaje (
                         id INT AUTO_INCREMENT,
                         remitenteId VARCHAR(10) NOT NULL,
                         destinatarioId VARCHAR(10) NOT NULL,
                         mensaje TEXT NOT NULL,
                         fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         leido BOOLEAN DEFAULT FALSE,
                         PRIMARY KEY (id),
                         FOREIGN KEY (remitenteId) REFERENCES Usuario(id),
                         FOREIGN KEY (destinatarioId) REFERENCES Usuario(id)
);

-- Insertar datos iniciales
INSERT INTO Departamento (codigo, nombre) VALUES('001','Administrador');
INSERT INTO Departamento (codigo, nombre) VALUES('002','Medico');
INSERT INTO Departamento (codigo, nombre) VALUES('003','Farmaceutico');

-- Insertar usuarios administradores
INSERT INTO Usuario (id, nombre, clave, departamento) VALUES('555','Admin One','555','001');
INSERT INTO Usuario (id, nombre, clave, departamento) VALUES('666','Admin Two','666','001');

INSERT INTO Administrador (id, nombre, departamento) VALUES('555','Admin One','001');
INSERT INTO Administrador (id, nombre, departamento) VALUES('666','Admin Two','001');

-- Datos de ejemplo para medicamentos
INSERT INTO Medicamento (codigo, nombre, presentacion) VALUES('MED001','Paracetamol','Tabletas 500mg');
INSERT INTO Medicamento (codigo, nombre, presentacion) VALUES('MED002','Ibuprofeno','Tabletas 400mg');
INSERT INTO Medicamento (codigo, nombre, presentacion) VALUES('MED003','Amoxicilina','Cápsulas 500mg');