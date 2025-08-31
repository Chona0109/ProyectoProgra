package data;

import logic.entidades.*;

import java.util.ArrayList;
import java.util.List;

public class data {
    private List<Medico> medicos;
    private List<Farmaceutico> farmaceuticos;
    private List<Administrador> administradores;
    private List<Paciente> pacientes;
    private List<Departamento> departamentos;

    public data() {
        medicos = new ArrayList<>();
        farmaceuticos = new ArrayList<>();
        administradores = new ArrayList<>();
        pacientes = new ArrayList<>();
        departamentos = new ArrayList<>();

        // Crear departamentos
        Departamento depAdmin = new Departamento("001", "Administrador");
        Departamento depMedico = new Departamento("002", "Medico");
        Departamento depFarmaceuta = new Departamento("003", "Farmaceutico");

        departamentos.add(depAdmin);
        departamentos.add(depMedico);
        departamentos.add(depFarmaceuta);

        // Médicos (ID, Nombre, Clave, Especialidad)
        Medico m1 = new Medico("111", "Juan Perez", "123", "Cardiología");
        m1.setDepartamento(depMedico);

        Medico m2 = new Medico("222", "Maria Lopez", "123", "Neurología");
        m2.setDepartamento(depMedico);

        medicos.add(m1);
        medicos.add(m2);

        // Farmaceuticos (ID, Nombre, Clave)
        Farmaceutico f1 = new Farmaceutico("333", "Carlos Ramos", "abc");
        f1.setDepartamento(depFarmaceuta);

        Farmaceutico f2 = new Farmaceutico("444", "Lucia Hernandez", "abc");
        f2.setDepartamento(depFarmaceuta);

        farmaceuticos.add(f1);
        farmaceuticos.add(f2);

        // Administradores (ID, Nombre, Clave)
        Administrador a1 = new Administrador("555", "Admin One", "adminpwd");
        a1.setDepartamento(depAdmin);

        Administrador a2 = new Administrador("666", "Admin Two", "adminpwd");
        a2.setDepartamento(depAdmin);

        administradores.add(a1);
        administradores.add(a2);

        // Pacientes (ID, Nombre, Fecha de Nacimiento, Teléfono)
        Paciente p1 = new Paciente("777", "Pedro Martinez", "1980-01-01", "555-6789");
        Paciente p2 = new Paciente("888", "Laura Gonzalez", "1990-05-15", "555-9876");

        pacientes.add(p1);
        pacientes.add(p2);
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public List<Farmaceutico> getFarmaceuticos() {
        return farmaceuticos;
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }
}
