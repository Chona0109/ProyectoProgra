package sistema.logic;

import sistema.data.data;
import sistema.logic.entities.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private static Service theInstance;
    private data data;

    private Service() {
        data = new data();
    }

    public static Service instance() {
        if (theInstance == null) {
            theInstance = new Service();
        }
        return theInstance;
    }

    // MÉDICOS
    public void create(Medico e) throws Exception {
        Medico result = data.getMedicos().stream()
                .filter(m -> m.getId().equals(e.getId()))
                .findFirst()
                .orElse(null);
        if (result == null) data.getMedicos().add(e);
        else throw new Exception("Médico ya existe");
    }

    public Medico read(Medico e) throws Exception {
        Medico result = data.getMedicos().stream()
                .filter(m -> m.getId().equals(e.getId()))
                .findFirst()
                .orElse(null);
        if (result != null) return result;
        else throw new Exception("Médico no existe");
    }

    public void delete(Medico e) throws Exception {
        Medico result = data.getMedicos().stream()
                .filter(m -> m.getId().equals(e.getId()))
                .findFirst()
                .orElse(null);
        if (result != null) data.getMedicos().remove(result);
        else throw new Exception("Médico no existe");
    }

    public List<Medico> findAllMedicos() {
        return data.getMedicos();
    }

    public List<Medico> searchMedicoByName(String name) {
        return data.getMedicos().stream()
                .filter(m -> m.getNombre().toLowerCase().contains(name.toLowerCase()))
                .sorted(Comparator.comparing(Medico::getNombre))
                .collect(Collectors.toList());
    }

    // DEPARTAMENTOS
    public List<Departamento> findAllDepartamentos() {
        return data.getDepartamentos();
    }

    public List<Departamento> search(Departamento filtro) {
        return data.getDepartamentos().stream()
                .filter(d -> d.getNombre().toLowerCase().contains(
                        filtro.getNombre() != null ? filtro.getNombre().toLowerCase() : ""
                ))
                .sorted(Comparator.comparing(Departamento::getNombre))
                .collect(Collectors.toList());
    }

    // MEDICAMENTOS

    public void create(Medicamento m) throws Exception {
        Medicamento result = data.getMedicamentos().stream()
                .filter(med -> med.getCodigo().equals(m.getCodigo()))
                .findFirst()
                .orElse(null);
        if (result == null) data.getMedicamentos().add(m);
        else throw new Exception("Medicamento ya existe");
    }

    public Medicamento read(Medicamento m) throws Exception {
        Medicamento result = data.getMedicamentos().stream()
                .filter(med -> med.getCodigo().equals(m.getCodigo()))
                .findFirst()
                .orElse(null);
        if (result != null) return result;
        else throw new Exception("Medicamento no existe");
    }

    public void delete(Medicamento m) throws Exception {
        Medicamento result = data.getMedicamentos().stream()
                .filter(med -> med.getCodigo().equals(m.getCodigo()))
                .findFirst()
                .orElse(null);
        if (result != null) data.getMedicamentos().remove(result);
        else throw new Exception("Medicamento no existe");
    }

    public List<Medicamento> findAllMedicamentos() {
        return data.getMedicamentos();
    }

    public List<Medicamento> searchMedicamentoByCodigo(String codigo) {
        return data.getMedicamentos().stream()
                .filter(med -> med.getCodigo().toLowerCase().contains(codigo.toLowerCase()))
                .sorted(Comparator.comparing(Medicamento::getCodigo))
                .collect(Collectors.toList());
    }

    // FARMACÉUTICOS

    public void create(Farmaceutico f) throws Exception {
        Farmaceutico result = data.getFarmaceuticos().stream()
                .filter(far -> far.getId().equals(f.getId()))
                .findFirst()
                .orElse(null);
        if (result == null) data.getFarmaceuticos().add(f);
        else throw new Exception("Farmacéutico ya existe");
    }

    public Farmaceutico read(Farmaceutico f) throws Exception {
        Farmaceutico result = data.getFarmaceuticos().stream()
                .filter(far -> far.getId().equals(f.getId()))
                .findFirst()
                .orElse(null);
        if (result != null) return result;
        else throw new Exception("Farmacéutico no existe");
    }

    public void delete(Farmaceutico f) throws Exception {
        Farmaceutico result = data.getFarmaceuticos().stream()
                .filter(far -> far.getId().equals(f.getId()))
                .findFirst()
                .orElse(null);
        if (result != null) data.getFarmaceuticos().remove(result);
        else throw new Exception("Farmacéutico no existe");
    }

    public List<Farmaceutico> findAllFarmaceuticos() {
        return data.getFarmaceuticos();
    }

    public List<Farmaceutico> searchFarmaceuticoByName(String name) {
        return data.getFarmaceuticos().stream()
                .filter(f -> f.getNombre().toLowerCase().contains(name.toLowerCase()))
                .sorted(java.util.Comparator.comparing(Farmaceutico::getNombre))
                .collect(java.util.stream.Collectors.toList());
    }

    //Pacientes

    public void create(Paciente p) throws Exception {
        Paciente result = data.getPacientes().stream()
                .filter(pac -> pac.getId().equals(p.getId()))
                .findFirst()
                .orElse(null);
        if (result == null) data.getPacientes().add(p);
        else throw new Exception("Paciente ya existe");
    }

    public Paciente read(Paciente p) throws Exception {
        Paciente result = data.getPacientes().stream()
                .filter(pac -> pac.getId().equals(p.getId()))
                .findFirst()
                .orElse(null);
        if (result != null) return result;
        else throw new Exception("Paciente no existe");
    }

    public void delete(Paciente p) throws Exception {
        Paciente result = data.getPacientes().stream()
                .filter(pac -> pac.getId().equals(p.getId()))
                .findFirst()
                .orElse(null);
        if (result != null) data.getPacientes().remove(result);
        else throw new Exception("Paciente no existe");
    }

    public List<Paciente> findAllPaciente() {
        return data.getPacientes();
    }

    public List<Paciente> searchPacienteByName(String name) {
        return data.getPacientes().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(name.toLowerCase()))
                .sorted(java.util.Comparator.comparing(Paciente::getNombre))
                .collect(java.util.stream.Collectors.toList());
    }

    //Usuario

    // En Service.java
    public void create(Usuario u) throws Exception {
        Usuario result = data.getUsuarios().stream()
                .filter(us -> us.getId().equals(u.getId()))
                .findFirst().orElse(null);
        if (result == null) data.getUsuarios().add(u);
        else throw new Exception("Usuario ya existe");
    }

    public Usuario read(Usuario u) throws Exception {
        Usuario result = data.getUsuarios().stream()
                .filter(us -> us.getId().equals(u.getId()))
                .findFirst().orElse(null);
        if (result != null) return result;
        else throw new Exception("Usuario no existe");
    }

    public Usuario login(Usuario usuario) throws Exception {
        Usuario logged = read(usuario);
        if (!logged.getClave().equals(usuario.getClave())) {
            throw new Exception("Clave o ID no coinciden");
        }
        Sesion.setUsuario(logged);
        return logged;
    }

    public String getRol(Usuario usuario) {
        if (usuario.getDepartamento() == null) return "Desconocido";
        return usuario.getDepartamento().getNombre();
    }

    public void updateUsuario(Usuario usuario) throws Exception {
        Usuario existente = data.getUsuarios().stream()
                .filter(us -> us.getId().equals(usuario.getId()))
                .findFirst()
                .orElse(null);

        if (existente == null) {
            throw new Exception("Usuario no existe");
        }

        // Aquí actualizas solo la contraseña, pero puedes extender a más atributos
        existente.setClave(usuario.getClave());
        // si quisieras actualizar nombre o departamento, también podrías
    }




}
