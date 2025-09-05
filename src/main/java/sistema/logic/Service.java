package sistema.logic;

import sistema.data.data;
import sistema.data.containers.*;
import sistema.logic.entities.*;
import sistema.persistence.GestorPersistencia;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private static Service theInstance;
    private data data;
    private GestorPersistencia gestorPersistencia;
    private int recetaCounter = 1;

    private Service() {
        // Inicializar el gestor de persistencia
        gestorPersistencia = new GestorPersistencia("sistema-data");

        // Cargar datos existentes o crear nuevos
        cargarDatos();
    }

    public static Service instance() {
        if (theInstance == null) {
            theInstance = new Service();
        }
        return theInstance;
    }

    /**
     * Carga todos los datos desde archivos XML o inicializa datos por defecto
     */
    private void cargarDatos() {
        try {
            data = new data(); // Inicializar estructura base

            // Cargar cada tipo de entidad
            cargarDepartamentos();
            cargarMedicos();
            cargarFarmaceuticos();
            cargarAdministradores();
            cargarPacientes();
            cargarMedicamentos();
            cargarRecetas();
            cargarUsuarios();

            System.out.println("Datos cargados exitosamente desde archivos XML");

        } catch (Exception e) {
            System.out.println("No se pudieron cargar datos existentes, inicializando datos por defecto");
            data = new data(); // Esto inicializará los datos por defecto
            guardarTodosLosDatos(); // Guardar los datos iniciales
        }
    }

    /**
     * Guarda todos los datos en archivos XML
     */
    public void guardarTodosLosDatos() {
        try {
            // Guardar departamentos
            DepartamentosContainer depContainer = new DepartamentosContainer();
            depContainer.setDepartamentos(data.getDepartamentos());
            gestorPersistencia.guardar(depContainer, "departamentos.xml");

            // Guardar médicos
            MedicosContainer medContainer = new MedicosContainer();
            medContainer.setMedicos(data.getMedicos());
            gestorPersistencia.guardar(medContainer, "medicos.xml");

            // Guardar farmacéuticos
            FarmaceuticosContainer farmContainer = new FarmaceuticosContainer();
            farmContainer.setFarmaceuticos(data.getFarmaceuticos());
            gestorPersistencia.guardar(farmContainer, "farmaceuticos.xml");

            // Guardar administradores
            AdministradoresContainer adminContainer = new AdministradoresContainer();
            adminContainer.setAdministradores(data.getAdministradores());
            gestorPersistencia.guardar(adminContainer, "administradores.xml");

            // Guardar pacientes
            PacientesContainer pacContainer = new PacientesContainer();
            pacContainer.setPacientes(data.getPacientes());
            gestorPersistencia.guardar(pacContainer, "pacientes.xml");

            // Guardar medicamentos
            MedicamentosContainer medicContainer = new MedicamentosContainer();
            medicContainer.setMedicamentos(data.getMedicamentos());
            gestorPersistencia.guardar(medicContainer, "medicamentos.xml");

            // Guardar recetas
            RecetasContainer recContainer = new RecetasContainer();
            recContainer.setRecetas(data.getRecetas());
            gestorPersistencia.guardar(recContainer, "recetas.xml");

            // Guardar usuarios
            UsuariosContainer userContainer = new UsuariosContainer();
            userContainer.setUsuarios(data.getUsuarios());
            gestorPersistencia.guardar(userContainer, "usuarios.xml");

            System.out.println("Todos los datos guardados exitosamente");

        } catch (Exception e) {
            System.err.println("Error al guardar datos: " + e.getMessage());
        }
    }

    // Métodos de carga individual
    private void cargarDepartamentos() {
        DepartamentosContainer container = gestorPersistencia.cargar(DepartamentosContainer.class, "departamentos.xml");
        if (container != null && container.getDepartamentos() != null) {
            data.getDepartamentos().clear();
            data.getDepartamentos().addAll(container.getDepartamentos());
        }
    }

    private void cargarMedicos() {
        MedicosContainer container = gestorPersistencia.cargar(MedicosContainer.class, "medicos.xml");
        if (container != null && container.getMedicos() != null) {
            data.getMedicos().clear();
            data.getMedicos().addAll(container.getMedicos());
        }
    }

    private void cargarFarmaceuticos() {
        FarmaceuticosContainer container = gestorPersistencia.cargar(FarmaceuticosContainer.class, "farmaceuticos.xml");
        if (container != null && container.getFarmaceuticos() != null) {
            data.getFarmaceuticos().clear();
            data.getFarmaceuticos().addAll(container.getFarmaceuticos());
        }
    }

    private void cargarAdministradores() {
        AdministradoresContainer container = gestorPersistencia.cargar(AdministradoresContainer.class, "administradores.xml");
        if (container != null && container.getAdministradores() != null) {
            data.getAdministradores().clear();
            data.getAdministradores().addAll(container.getAdministradores());
        }
    }

    private void cargarPacientes() {
        PacientesContainer container = gestorPersistencia.cargar(PacientesContainer.class, "pacientes.xml");
        if (container != null && container.getPacientes() != null) {
            data.getPacientes().clear();
            data.getPacientes().addAll(container.getPacientes());
        }
    }

    private void cargarMedicamentos() {
        MedicamentosContainer container = gestorPersistencia.cargar(MedicamentosContainer.class, "medicamentos.xml");
        if (container != null && container.getMedicamentos() != null) {
            data.getMedicamentos().clear();
            data.getMedicamentos().addAll(container.getMedicamentos());
        }
    }

    private void cargarRecetas() {
        RecetasContainer container = gestorPersistencia.cargar(RecetasContainer.class, "recetas.xml");
        if (container != null && container.getRecetas() != null) {
            data.getRecetas().clear();
            data.getRecetas().addAll(container.getRecetas());
        }
    }

    private void cargarUsuarios() {
        UsuariosContainer container = gestorPersistencia.cargar(UsuariosContainer.class, "usuarios.xml");
        if (container != null && container.getUsuarios() != null) {
            data.getUsuarios().clear();
            data.getUsuarios().addAll(container.getUsuarios());
        }
    }

    // MÉDICOS - Modificados para incluir persistencia
    public void create(Medico e) throws Exception {
        Medico result = data.getMedicos().stream()
                .filter(m -> m.getId().equals(e.getId()))
                .findFirst()
                .orElse(null);
        if (result == null) {
            data.getMedicos().add(e);
            guardarTodosLosDatos(); // Guardar después de cada cambio
        } else {
            throw new Exception("Médico ya existe");
        }
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
        if (result != null) {
            data.getMedicos().remove(result);
            guardarTodosLosDatos(); // Guardar después de eliminar
        } else {
            throw new Exception("Médico no existe");
        }
    }

    // MEDICAMENTOS - Modificados para incluir persistencia
    public void create(Medicamento m) throws Exception {
        Medicamento result = data.getMedicamentos().stream()
                .filter(med -> med.getCodigo().equals(m.getCodigo()))
                .findFirst()
                .orElse(null);
        if (result == null) {
            data.getMedicamentos().add(m);
            guardarTodosLosDatos();
        } else {
            throw new Exception("Medicamento ya existe");
        }
    }

    public void delete(Medicamento m) throws Exception {
        Medicamento result = data.getMedicamentos().stream()
                .filter(med -> med.getCodigo().equals(m.getCodigo()))
                .findFirst()
                .orElse(null);
        if (result != null) {
            data.getMedicamentos().remove(result);
            guardarTodosLosDatos();
        } else {
            throw new Exception("Medicamento no existe");
        }
    }

    // RECETAS - Modificadas para incluir persistencia
    public void createReceta(Receta r) throws Exception {
        if (r.getId() == null || r.getId().isEmpty()) {
            r.setId("R" + recetaCounter++);
        }

        Receta result = data.getRecetas().stream()
                .filter(rec -> rec.getId().equals(r.getId()))
                .findFirst()
                .orElse(null);
        if (result == null) {
            data.getRecetas().add(r);
            guardarTodosLosDatos();
        } else {
            throw new Exception("Receta ya existe");
        }
    }

    public void updateReceta(Receta r) throws Exception {
        Receta existente = data.getRecetas().stream()
                .filter(rec -> rec.getId().equals(r.getId()))
                .findFirst()
                .orElse(null);

        if (existente == null) throw new Exception("Receta no existe");

        existente.setPaciente(r.getPaciente());
        existente.setMedico(r.getMedico());
        existente.setFechaConfeccion(r.getFechaConfeccion());
        existente.setFechaRetiro(r.getFechaRetiro());
        existente.setEstado(r.getEstado());
        existente.setMedicamentos(r.getMedicamentos());

        guardarTodosLosDatos(); // Guardar después de actualizar
    }

    public void deleteReceta(Receta r) throws Exception {
        Receta existente = data.getRecetas().stream()
                .filter(rec -> rec.getId().equals(r.getId()))
                .findFirst()
                .orElse(null);
        if (existente != null) {
            data.getRecetas().remove(existente);
            guardarTodosLosDatos();
        } else {
            throw new Exception("Receta no existe");
        }
    }

    // FARMACÉUTICOS - Modificados para incluir persistencia
    public void create(Farmaceutico f) throws Exception {
        Farmaceutico result = data.getFarmaceuticos().stream()
                .filter(far -> far.getId().equals(f.getId()))
                .findFirst()
                .orElse(null);
        if (result == null) {
            data.getFarmaceuticos().add(f);
            guardarTodosLosDatos();
        } else {
            throw new Exception("Farmacéutico ya existe");
        }
    }

    public void delete(Farmaceutico f) throws Exception {
        Farmaceutico result = data.getFarmaceuticos().stream()
                .filter(far -> far.getId().equals(f.getId()))
                .findFirst()
                .orElse(null);
        if (result != null) {
            data.getFarmaceuticos().remove(result);
            guardarTodosLosDatos();
        } else {
            throw new Exception("Farmacéutico no existe");
        }
    }

    // PACIENTES - Modificados para incluir persistencia
    public void create(Paciente p) throws Exception {
        Paciente result = data.getPacientes().stream()
                .filter(pac -> pac.getId().equals(p.getId()))
                .findFirst()
                .orElse(null);
        if (result == null) {
            data.getPacientes().add(p);
            guardarTodosLosDatos();
        } else {
            throw new Exception("Paciente ya existe");
        }
    }

    public void delete(Paciente p) throws Exception {
        Paciente result = data.getPacientes().stream()
                .filter(pac -> pac.getId().equals(p.getId()))
                .findFirst()
                .orElse(null);
        if (result != null) {
            data.getPacientes().remove(result);
            guardarTodosLosDatos();
        } else {
            throw new Exception("Paciente no existe");
        }
    }

    // USUARIOS - Modificados para incluir persistencia
    public void create(Usuario u) throws Exception {
        Usuario result = data.getUsuarios().stream()
                .filter(us -> us.getId().equals(u.getId()))
                .findFirst().orElse(null);
        if (result == null) {
            data.getUsuarios().add(u);
            guardarTodosLosDatos();
        } else {
            throw new Exception("Usuario ya existe");
        }
    }

    public void updateUsuario(Usuario usuario) throws Exception {
        Usuario existente = data.getUsuarios().stream()
                .filter(us -> us.getId().equals(usuario.getId()))
                .findFirst()
                .orElse(null);

        if (existente == null) {
            throw new Exception("Usuario no existe");
        }

        existente.setClave(usuario.getClave());
        guardarTodosLosDatos(); // Guardar después de actualizar
    }

    // MÉTODOS DE LECTURA (sin cambios, no requieren persistencia)
    public List<Medico> findAllMedicos() {
        return data.getMedicos();
    }

    public List<Medico> searchMedicoByName(String name) {
        return data.getMedicos().stream()
                .filter(m -> m.getNombre().toLowerCase().contains(name.toLowerCase()))
                .sorted(Comparator.comparing(Medico::getNombre))
                .collect(Collectors.toList());
    }

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

    public Medicamento read(Medicamento m) throws Exception {
        Medicamento result = data.getMedicamentos().stream()
                .filter(med -> med.getCodigo().equals(m.getCodigo()))
                .findFirst()
                .orElse(null);
        if (result != null) return result;
        else throw new Exception("Medicamento no existe");
    }

    public List<Medicamento> searchMedicamentoByName(String name) {
        return data.getMedicamentos().stream()
                .filter(m -> m.getNombre().toLowerCase().contains(name.toLowerCase()))
                .sorted(Comparator.comparing(Medicamento::getNombre))
                .collect(Collectors.toList());
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

    public Receta readReceta(Receta r) throws Exception {
        Receta result = data.getRecetas().stream()
                .filter(rec -> rec.getId().equals(r.getId()))
                .findFirst()
                .orElse(null);
        if (result != null) return result;
        else throw new Exception("Receta no existe");
    }

    public Receta readRecetaById(String id) throws Exception {
        Receta temp = new Receta();
        temp.setId(id);
        return this.readReceta(temp);
    }

    public Receta findRecetaById(String id) throws Exception {
        Receta result = data.getRecetas().stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (result != null) return result;
        else throw new Exception("Receta no existe");
    }

    public List<Receta> findAllRecetas() {
        return data.getRecetas();
    }

    public List<Receta> searchRecetaByPaciente(String nombrePaciente) {
        return data.getRecetas().stream()
                .filter(r -> r.getPaciente() != null &&
                        r.getPaciente().getNombre().toLowerCase().contains(nombrePaciente.toLowerCase()))
                .collect(Collectors.toList());
    }

    public void addMedicamentoToReceta(String recetaId, MedicamentoDetalle detalle) throws Exception {
        Receta r = readReceta(new Receta(){{
            setId(recetaId);
        }});
        r.getMedicamentos().add(detalle);
        guardarTodosLosDatos(); // Guardar después de modificar
    }

    public void removeMedicamentoFromReceta(String recetaId, int index) throws Exception {
        Receta r = readReceta(new Receta(){{
            setId(recetaId);
        }});
        if (index >= 0 && index < r.getMedicamentos().size()) {
            r.getMedicamentos().remove(index);
            guardarTodosLosDatos(); // Guardar después de modificar
        } else {
            throw new Exception("Índice de medicamento inválido");
        }
    }

    public Farmaceutico read(Farmaceutico f) throws Exception {
        Farmaceutico result = data.getFarmaceuticos().stream()
                .filter(far -> far.getId().equals(f.getId()))
                .findFirst()
                .orElse(null);
        if (result != null) return result;
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

    public Paciente read(Paciente p) throws Exception {
        Paciente result = data.getPacientes().stream()
                .filter(pac -> pac.getId().equals(p.getId()))
                .findFirst()
                .orElse(null);
        if (result != null) return result;
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

    public List<Paciente> searchPacienteById(String id) {
        return data.getPacientes().stream()
                .filter(p -> p.getId().toLowerCase().contains(id.toLowerCase()))
                .sorted(Comparator.comparing(Paciente::getId))
                .collect(Collectors.toList());
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

    public void guardarManualmente() {
        guardarTodosLosDatos();
    }

    public void recargarDatos() {
        cargarDatos();
    }
}