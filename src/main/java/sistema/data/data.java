package sistema.data;

import sistema.logic.entities.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class data {
    private List<Medico> medicos;
    private List<Farmaceutico> farmaceuticos;
    private List<Administrador> administradores;
    private List<Paciente> pacientes;
    private List<Departamento> departamentos;
    private List<Receta> recetas;
    private List<Medicamento> medicamentos;

    public data() {
        medicos = new ArrayList<>();
        farmaceuticos = new ArrayList<>();
        administradores = new ArrayList<>();
        pacientes = new ArrayList<>();
        departamentos = new ArrayList<>();
        recetas = new ArrayList<>();
        medicamentos = new ArrayList<>();


        Departamento depAdmin = new Departamento("001", "Administrador");
        Departamento depMedico = new Departamento("002", "Medico");
        Departamento depFarmaceuta = new Departamento("003", "Farmaceutico");

        departamentos.add(depAdmin);
        departamentos.add(depMedico);
        departamentos.add(depFarmaceuta);


        Medico m1 = new Medico("111", "Juan Perez", "123", "Cardiología");
        m1.setDepartamento(depMedico);

        Medico m2 = new Medico("222", "Maria Lopez", "123", "Neurología");
        m2.setDepartamento(depMedico);

        medicos.add(m1);
        medicos.add(m2);


        Farmaceutico f1 = new Farmaceutico("333", "Carlos Ramos", "abc");
        f1.setDepartamento(depFarmaceuta);

        Farmaceutico f2 = new Farmaceutico("444", "Lucia Hernandez", "abc");
        f2.setDepartamento(depFarmaceuta);

        farmaceuticos.add(f1);
        farmaceuticos.add(f2);


        Administrador a1 = new Administrador("555", "Admin One", "adminpwd");
        a1.setDepartamento(depAdmin);

        Administrador a2 = new Administrador("666", "Admin Two", "adminpwd");
        a2.setDepartamento(depAdmin);

        administradores.add(a1);
        administradores.add(a2);


        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        Paciente p1 = new Paciente("777", "Pedro Martinez", LocalDate.parse("1980-01-01", formatter), "555-6789");
        Paciente p2 = new Paciente("888", "Laura Gonzalez", LocalDate.parse("1990-05-15", formatter), "555-9876");

        pacientes.add(p1);
        pacientes.add(p2);

        Medicamento med1 = new Medicamento("001","Acetaminofen 100mg");
        Medicamento med2 = new Medicamento("002","Acetaminofen 500mg");
        medicamentos.add(med1);
        medicamentos.add(med2);


        Receta r1 = new Receta(m1,p1,med1,"100mg", "Una cada 8 Horas");
        recetas.add(r1);

        usuarios.add(new Usuario("111", "Juan Perez", "123")); // médico
        usuarios.add(new Usuario("333", "Carlos Ramos", "abc")); // farmacéutico
        usuarios.add(new Usuario("555", "Admin One", "adminpwd")); // administrador
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

    public List<Receta> getRecetas() {return recetas;}

    public List<Medicamento> getMedicamentos() {return medicamentos;}

    private List<Usuario> usuarios = new ArrayList<>();

    public List<Usuario> getUsuarios() { return usuarios; }

}
