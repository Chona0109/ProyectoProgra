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


        Medico m1 = new Medico("111", "Juan Perez", "Cardiología");
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


        Medicamento med1 = new Medicamento("111", "Acetaminofen", "500");
        Medicamento med2 = new Medicamento("222", "Panadol", "100mg");
        medicamentos.add(med1);
        medicamentos.add(med2);


        MedicamentoDetalle det1 = new MedicamentoDetalle(med1, 2, "Tomar 2 veces al día", 5);
        MedicamentoDetalle det2 = new MedicamentoDetalle(med2, 1, "Tomar después de comida", 7);


        Receta r1 = new Receta(medicos.get(0), pacientes.get(0));
        r1.setId("R001");
        r1.agregarMedicamento(det1);
        r1.agregarMedicamento(det2);
        r1.setEstado("CONFECCIONADA");

        Receta r2 = new Receta(medicos.get(1), pacientes.get(1));
        r2.setId("R002");
        r2.agregarMedicamento(det2);
        r2.setEstado("DESPACHADA");


        recetas.add(r1);
        recetas.add(r2);


        usuarios.add(m1);
        usuarios.add(m2);
        usuarios.add(f1);
        usuarios.add(f2);
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