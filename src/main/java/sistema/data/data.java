package sistema.data;

import sistema.logic.entities.*;
import jakarta.xml.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class data {
    @XmlElementWrapper(name = "medicos")
    @XmlElement(name = "medico")
    private List<Medico> medicos;

    @XmlElementWrapper(name = "farmaceuticos")
    @XmlElement(name = "farmaceutico")
    private List<Farmaceutico> farmaceuticos;

    @XmlElementWrapper(name = "administradores")
    @XmlElement(name = "administrador")
    private List<Administrador> administradores;

    @XmlElementWrapper(name = "pacientes")
    @XmlElement(name = "paciente")
    private List<Paciente> pacientes;

    @XmlElementWrapper(name = "departamentos")
    @XmlElement(name = "departamento")
    private List<Departamento> departamentos;

    @XmlElementWrapper(name = "recetas")
    @XmlElement(name = "receta")
    private List<Receta> recetas;

    @XmlElementWrapper(name = "medicamentos")
    @XmlElement(name = "medicamento")
    private List<Medicamento> medicamentos;

    @XmlElementWrapper(name = "usuarios")
    @XmlElement(name = "usuario")
    private List<Usuario> usuarios;

    public data() {
        medicos = new ArrayList<>();
        farmaceuticos = new ArrayList<>();
        administradores = new ArrayList<>();
        pacientes = new ArrayList<>();
        departamentos = new ArrayList<>();
        recetas = new ArrayList<>();
        medicamentos = new ArrayList<>();
        usuarios = new ArrayList<>();


        Departamento depAdmin = new Departamento("001", "Administrador");
        Departamento depMedico = new Departamento("002", "Medico");
        Departamento depFarmaceuta = new Departamento("003", "Farmaceutico");

        departamentos.add(depAdmin);
        departamentos.add(depMedico);
        departamentos.add(depFarmaceuta);

        Administrador a1 = new Administrador("555", "Admin One");
        a1.setDepartamento(depAdmin);

        Administrador a2 = new Administrador("666", "Admin Two");
        a2.setDepartamento(depAdmin);

        administradores.add(a1);
        administradores.add(a2);

        usuarios.add(a1);
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

    public List<Receta> getRecetas() {
        return recetas;
    }

    public List<Medicamento> getMedicamentos() {
        return medicamentos;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }


    public void setMedicos(List<Medico> medicos) {
        this.medicos = medicos;
    }

    public void setFarmaceuticos(List<Farmaceutico> farmaceuticos) {
        this.farmaceuticos = farmaceuticos;
    }

    public void setAdministradores(List<Administrador> administradores) {
        this.administradores = administradores;
    }

    public void setPacientes(List<Paciente> pacientes) {
        this.pacientes = pacientes;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }
}