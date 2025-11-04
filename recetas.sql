CREATE DATABASE Recetas;
USE Recetas;


CREATE TABLE Departamento (
                              codigo VARCHAR(10) NOT NULL,
                              nombre VARCHAR(30) NOT NULL,
                              PRIMARY KEY (codigo)
);


CREATE TABLE Usuario (
                         id VARCHAR(10) NOT NULL,
                         nombre VARCHAR(50) NOT NULL,
                         clave VARCHAR(50) NOT NULL,
                         departamento VARCHAR(10) NOT NULL,
                         PRIMARY KEY (id),
                         FOREIGN KEY (departamento) REFERENCES Departamento(codigo) ON DELETE CASCADE
);


CREATE TABLE Medico (
                        id VARCHAR(10) NOT NULL,
                        nombre VARCHAR(50) NOT NULL,
                        especialidad VARCHAR(50) NOT NULL,
                        departamento VARCHAR(10) NOT NULL,
                        PRIMARY KEY (id),
                        FOREIGN KEY (departamento) REFERENCES Departamento(codigo) ON DELETE CASCADE,
                        FOREIGN KEY (id) REFERENCES Usuario(id) ON DELETE CASCADE
);


CREATE TABLE Farmaceutico (
                              id VARCHAR(10) NOT NULL,
                              nombre VARCHAR(50) NOT NULL,
                              departamento VARCHAR(10) NOT NULL,
                              PRIMARY KEY (id),
                              FOREIGN KEY (departamento) REFERENCES Departamento(codigo) ON DELETE CASCADE,
                              FOREIGN KEY (id) REFERENCES Usuario(id) ON DELETE CASCADE
);


CREATE TABLE Administrador (
                               id VARCHAR(10) NOT NULL,
                               nombre VARCHAR(50) NOT NULL,
                               departamento VARCHAR(10) NOT NULL,
                               PRIMARY KEY (id),
                               FOREIGN KEY (departamento) REFERENCES Departamento(codigo) ON DELETE CASCADE,
                               FOREIGN KEY (id) REFERENCES Usuario(id) ON DELETE CASCADE
);


CREATE TABLE Paciente (
                          id VARCHAR(10) NOT NULL,
                          nombre VARCHAR(50) NOT NULL,
                          fechaNacimiento DATE,
                          telefono VARCHAR(20),
                          PRIMARY KEY (id)
);


CREATE TABLE Medicamento (
                             codigo VARCHAR(10) NOT NULL,
                             nombre VARCHAR(100) NOT NULL,
                             presentacion VARCHAR(100),
                             PRIMARY KEY (codigo)
);


CREATE TABLE Receta (
                        id INT AUTO_INCREMENT,
                        medicoId VARCHAR(10) NOT NULL,
                        pacienteId VARCHAR(10) NOT NULL,
                        fechaConfeccion DATE NOT NULL,
                        fechaRetiro DATE,
                        estado VARCHAR(20) NOT NULL,
                        PRIMARY KEY (id),
                        FOREIGN KEY (medicoId) REFERENCES Medico(id) ON DELETE CASCADE,
                        FOREIGN KEY (pacienteId) REFERENCES Paciente(id) ON DELETE CASCADE
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
                                    FOREIGN KEY (medicamentoCodigo) REFERENCES Medicamento(codigo) ON DELETE CASCADE
);


CREATE TABLE Mensaje (
                         id INT AUTO_INCREMENT,
                         remitenteId VARCHAR(10) NOT NULL,
                         destinatarioId VARCHAR(10) NOT NULL,
                         mensaje TEXT NOT NULL,
                         fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         leido BOOLEAN DEFAULT FALSE,
                         PRIMARY KEY (id),
                         FOREIGN KEY (remitenteId) REFERENCES Usuario(id) ON DELETE CASCADE,
                         FOREIGN KEY (destinatarioId) REFERENCES Usuario(id) ON DELETE CASCADE
);


INSERT INTO Departamento (codigo, nombre) VALUES('001','Administrador');
INSERT INTO Departamento (codigo, nombre) VALUES('002','Medico');
INSERT INTO Departamento (codigo, nombre) VALUES('003','Farmaceutico');


INSERT INTO Usuario (id, nombre, clave, departamento) VALUES('555','Admin One','555','001');
INSERT INTO Usuario (id, nombre, clave, departamento) VALUES('666','Admin Two','666','001');

INSERT INTO Administrador (id, nombre, departamento) VALUES('555','Admin One','001');
INSERT INTO Administrador (id, nombre, departamento) VALUES('666','Admin Two','001');


INSERT INTO Medicamento (codigo, nombre, presentacion) VALUES('MED001','Paracetamol','Tabletas 500mg');
INSERT INTO Medicamento (codigo, nombre, presentacion) VALUES('MED002','Ibuprofeno','Tabletas 400mg');
INSERT INTO Medicamento (codigo, nombre, presentacion) VALUES('MED003','Amoxicilina','Cápsulas 500mg');


INSERT INTO Usuario (id, nombre, clave, departamento)
VALUES
    ('777', 'Farmaceutico Uno', '777', '003'),
    ('778', 'Farmaceutico Dos', '778', '003');


INSERT INTO Farmaceutico (id, nombre, departamento)
VALUES
    ('777', 'Farmaceutico Uno', '003'),
    ('778', 'Farmaceutico Dos', '003');


INSERT INTO Usuario (id, nombre, clave, departamento)
VALUES
    ('888', 'Medico Uno', '888', '002'),
    ('889', 'Medico Dos', '889', '002');


INSERT INTO Medico (id, nombre, especialidad, departamento)
VALUES
    ('888', 'Medico Uno', 'Pediatría', '002'),
    ('889', 'Medico Dos', 'Cardiología', '002');


INSERT INTO Paciente (id, nombre, fechaNacimiento, telefono)
VALUES ('P001', 'Juan Pérez', '1990-05-12', '8888-1234');


INSERT INTO Receta (medicoId, pacienteId, fechaConfeccion, fechaRetiro, estado)
VALUES
    ('888', 'P001', CURDATE(), NULL, 'CONFECCIONADA');


INSERT INTO MedicamentoDetalle (recetaId, medicamentoCodigo, cantidad, indicaciones, dias)
VALUES
    (1, 'MED001', 10, 'Tomar cada 8 horas', 5),
    (1, 'MED002', 5, 'Tomar cada 12 horas', 3);