package logic;

import data.data;
import logic.entidades.*;

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

    // USUARIOS
    public void create(Usuario u) throws Exception {
        if (u instanceof Medico) create((Medico) u);
        else throw new Exception("Tipo de usuario no soportado");
    }

    public Usuario read(Usuario u) throws Exception {
        if (u instanceof Medico) return read((Medico) u);
        else throw new Exception("Tipo de usuario no soportado");
    }

    public void delete(Usuario u) throws Exception {
        if (u instanceof Medico) delete((Medico) u);
        else throw new Exception("Tipo de usuario no soportado");
    }

    public List<Usuario> findAllUsuarios() {

        return (List<Usuario>) (List<?>) data.getMedicos();
    }
    
    // Medicamentos
    public List<Medicamento> findAllMedicamentos() {
        return (List<Medicamento>) (List<?>) data.getMedicos();
    }
    public void create(Medicamento u) throws Exception {
        if (u instanceof Medicamento) create((Medicamento) u);
        else throw new Exception("Tipo de usuario no soportado");
    }

    public Medicamento read(Medicamento u) throws Exception {
        if (u instanceof Medicamento) return read((Medicamento) u);
        else throw new Exception("Tipo de usuario no soportado");
    }
    public void delete(Medicamento u) throws Exception {
        if (u instanceof Medicamento) delete((Medicamento) u);
        else throw new Exception("Tipo de usuario no soportado");
    }
}
