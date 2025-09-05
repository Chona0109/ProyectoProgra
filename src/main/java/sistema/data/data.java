package sistema.data;

import sistema.logic.entities.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class    data {
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


        Medico m1 = new Medico("111", "Juan Perez",  "Cardiología");
        m1.setDepartamento(depMedico);

        Medico m2 = new Medico("222", "Maria Lopez", "Neurología");
        m2.setDepartamento(depMedico);

        medicos.add(m1);
        medicos.add(m2);


        Farmaceutico f1 = new Farmaceutico("333", "Carlos Ramos");
        f1.setDepartamento(depFarmaceuta);

        Farmaceutico f2 = new Farmaceutico("444", "Lucia Hernandez");
        f2.setDepartamento(depFarmaceuta);

        farmaceuticos.add(f1);
        farmaceuticos.add(f2);


        Administrador a1 = new Administrador("555", "Admin One");
        a1.setDepartamento(depAdmin);

        Administrador a2 = new Administrador("666", "Admin Two");
        a2.setDepartamento(depAdmin);

        administradores.add(a1);
        administradores.add(a2);


        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;

        Paciente p1 = new Paciente("777", "Pedro Martinez", LocalDate.parse("1980-01-01", formatter), "555-6789");
        Paciente p2 = new Paciente("888", "Laura Gonzalez", LocalDate.parse("1990-05-15", formatter), "555-9876");

        pacientes.add(p1);
        pacientes.add(p2);

        Medicamento med1 = new Medicamento("111","Acetaminofen", "500");
        Medicamento med2 = new Medicamento("222","Panadol","100mg");
        medicamentos.add(med1);
        medicamentos.add(med2);




        usuarios.add(m1); // ya tiene depMedico
        usuarios.add(m2);
        usuarios.add(f1); // ya tiene depFarmaceuta
        usuarios.add(f2);
        usuarios.add(a1); // ya tiene depAdmin
        usuarios.add(a2);
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
