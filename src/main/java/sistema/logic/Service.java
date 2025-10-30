package sistema.logic;

import sistema.data.*;

import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private static Service theInstance;

    private DepartamentoDao departamentoDao;
    private UsuarioDao usuarioDao;
    private MedicoDao medicoDao;
    private FarmaceuticoDao farmaceuticoDao;
    private PacienteDao pacienteDao;
    private MedicamentoDao medicamentoDao;
    private RecetaDao recetaDao;

    public static Service instance() {
        if (theInstance == null) {
            theInstance = new Service();
        }
        return theInstance;
    }

    private Service() {
        try {
            departamentoDao = new DepartamentoDao();
            usuarioDao = new UsuarioDao();
            medicoDao = new MedicoDao();
            farmaceuticoDao = new FarmaceuticoDao();
            pacienteDao = new PacienteDao();
            medicamentoDao = new MedicamentoDao();
            recetaDao = new RecetaDao();
        } catch (Exception e) {
            System.err.println("Error al inicializar Service: " + e.getMessage());
            System.exit(-1);
        }
    }

    public void stop() {
        try {
            Database.instance().close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // =============== MEDICOS ===============
    public void create(Medico m) throws Exception {
        try {
            medicoDao.read(m.getId());
            throw new Exception("Médico ya existe");
        } catch (Exception e) {}

        Departamento dep = departamentoDao.read("002");
        m.setDepartamento(dep);

        if (m.getClave() == null || m.getClave().isEmpty()) {
            m.setClave(m.getId());
        }

        medicoDao.create(m);
    }

    public void updateMedico(Medico medico) throws Exception {
        medicoDao.update(medico);
    }

    public Medico read(Medico e) throws Exception {
        return medicoDao.read(e.getId());
    }

    public void delete(Medico e) throws Exception {
        medicoDao.delete(e);
    }

    public List<Medico> findAllMedicos() {
        return medicoDao.findAll();
    }

    // =============== MEDICAMENTOS ===============
    public void create(Medicamento m) throws Exception {
        try {
            medicamentoDao.read(m.getCodigo());
            throw new Exception("Medicamento ya existe");
        } catch (Exception e) {
        }
        medicamentoDao.create(m);
    }

    public Medicamento read(Medicamento m) throws Exception {
        return medicamentoDao.read(m.getCodigo());
    }

    public List<Medicamento> searchMedicamentoByName(String name) {
        return medicamentoDao.searchByName(name);
    }

    public void delete(Medicamento m) throws Exception {
        medicamentoDao.delete(m);
    }

    public void updateMedicamento(Medicamento medicamento) throws Exception {
        medicamentoDao.update(medicamento);
    }

    public List<Medicamento> findAllMedicamentos() {
        return medicamentoDao.findAll();
    }

    public List<Medicamento> searchMedicamentoByCodigo(String codigo) {
        return medicamentoDao.searchByCodigo(codigo);
    }

    // =============== RECETAS ===============


    public Receta createReceta(Receta r) throws Exception {
        if (r.getMedico() == null) {
            Usuario usuarioLogueado = Sesion.getUsuario();
            if (usuarioLogueado == null) {
                throw new Exception("No hay usuario logueado");
            }
            if (usuarioLogueado.getDepartamento() != null &&
                    usuarioLogueado.getDepartamento().getCodigo().equals("002")) {
                Medico m = medicoDao.read(usuarioLogueado.getId());
                r.setMedico(m);
            } else {
                throw new Exception("Usuario no autorizado o médico no asignado");
            }
        }

        recetaDao.create(r);
        return r;
    }

    public List<Receta> searchRecetaById(String id) {
        if (id == null || id.isEmpty()) {
            return findAllRecetas();
        }
        return recetaDao.searchById(id);
    }

    public void updateReceta(Receta r) throws Exception {
        recetaDao.update(r);
    }

    public Receta readReceta(Receta r) throws Exception {
        return recetaDao.read(r.getId());
    }

    public String generarDetallesReceta(Receta receta) {
        if (receta == null) return "Receta no disponible";

        StringBuilder detalles = new StringBuilder();
        detalles.append("ID: ").append(getValue(receta.getId(), "Sin ID")).append("\n");
        detalles.append("Estado: ").append(getValue(receta.getEstado(), "Sin estado")).append("\n");
        detalles.append("Fecha Confección: ").append(getValue(receta.getFechaConfeccion(), "Sin fecha")).append("\n");
        detalles.append("Fecha Retiro: ").append(getValue(receta.getFechaRetiro(), "Sin fecha")).append("\n");
        detalles.append("Paciente: ").append(getValue(receta.getPaciente() != null ? receta.getPaciente().getNombre() : null, "Sin paciente")).append("\n");
        detalles.append("ID Paciente: ").append(getValue(receta.getPaciente() != null ? receta.getPaciente().getId() : null, "Sin ID")).append("\n");
        detalles.append("Médico: ").append(getValue(receta.getMedico() != null ? receta.getMedico().getNombre() : null, "Sin médico")).append("\n");

        if (receta.getMedicamentos() != null && !receta.getMedicamentos().isEmpty()) {
            detalles.append("Cantidad de Medicamentos: ").append(receta.getMedicamentos().size()).append("\n");

            for (MedicamentoDetalle detalle : receta.getMedicamentos()) {
                if (detalle != null) {
                    detalles.append(" - ");
                    detalles.append(detalle.getMedicamento() != null ? detalle.getMedicamento().getNombre() : "Medicamento no disponible");
                    detalles.append(", Cantidad: ").append(detalle.getCantidad());
                    detalles.append(", Indicaciones: ").append(getValue(detalle.getIndicaciones(), "Sin indicaciones"));
                    detalles.append(", Días: ").append(detalle.getDias());
                    detalles.append("\n");
                } else {
                    detalles.append(" - Detalle de medicamento no disponible\n");
                }
            }
        } else {
            detalles.append("Cantidad de Medicamentos: 0\n");
            detalles.append("No hay medicamentos registrados\n");
        }

        return detalles.toString();
    }

    private String getValue(Object value, String defaultValue) {
        return value != null ? value.toString() : defaultValue;
    }

    public void avanzarEstado(Receta receta) throws Exception {
        if (receta == null) throw new Exception("Receta inválida");

        switch (receta.getEstado()) {
            case "CONFECCIONADA":
                receta.setEstado("PROCESO");
                break;
            case "PROCESO":
                receta.setEstado("LISTA");
                break;
            case "LISTA":
                receta.setEstado("ENTREGADA");
                break;
            default:
                throw new Exception("No se puede avanzar más el estado desde: " + receta.getEstado());
        }

        recetaDao.update(receta);
    }

    public Receta findRecetaById(int id) throws Exception {
        return recetaDao.read(id);
    }

    public List<Receta> findAllRecetas() {
        return recetaDao.findAll();
    }

    public List<Receta> searchRecetaByIdPaciente(String idPaciente) {
        return recetaDao.searchByPacienteId(idPaciente);
    }

    public void removeMedicamentoFromReceta(int recetaId, int index) throws Exception {
        Receta r = recetaDao.read(recetaId);
        if (index >= 0 && index < r.getMedicamentos().size()) {
            r.getMedicamentos().remove(index);
            recetaDao.update(r);
        } else {
            throw new Exception("Índice de medicamento inválido");
        }
    }

    // Modificar detalle de medicamento en una receta (en memoria, antes de guardar)
    public Receta modificarDetalleMedicamento(Window parent, Receta receta, int row) throws Exception {
        // Validar que la receta tenga medicamentos
        if (receta == null) {
            throw new Exception("Receta inválida");
        }

        if (receta.getMedicamentos() == null || receta.getMedicamentos().isEmpty()) {
            throw new Exception("La receta no tiene medicamentos");
        }

        if (row < 0 || row >= receta.getMedicamentos().size()) {
            throw new Exception("Índice inválido para modificar detalle");
        }

        MedicamentoDetalle detalle = receta.getMedicamentos().get(row);

        sistema.presentation.prescribirModificarDetalle.prescribirModificarDetalle dialog =
                new sistema.presentation.prescribirModificarDetalle.prescribirModificarDetalle(parent, detalle);
        dialog.setVisible(true);

        if (dialog.isGuardado()) {
            receta.getMedicamentos().set(row, dialog.take());
        }

        return receta;
    }

    // =============== FARMACEUTICOS ===============
    public void create(Farmaceutico f) throws Exception {
        try {
            farmaceuticoDao.read(f.getId());
            throw new Exception("Farmacéutico ya existe");
        } catch (Exception e) {}

        Departamento dep = departamentoDao.read("003");
        f.setDepartamento(dep);

        if (f.getClave() == null || f.getClave().isEmpty()) {
            f.setClave(f.getId());
        }

        farmaceuticoDao.create(f);
    }

    public Farmaceutico read(Farmaceutico f) throws Exception {
        return farmaceuticoDao.read(f.getId());
    }

    public List<Farmaceutico> findAllFarmaceuticos() {
        return farmaceuticoDao.findAll();
    }

    public List<Farmaceutico> searchFarmaceuticoByName(String name) {
        return farmaceuticoDao.searchByName(name);
    }

    public void delete(Farmaceutico f) throws Exception {
        farmaceuticoDao.delete(f);
    }

    public void updateFarmaceutico(Farmaceutico farmaceutico) throws Exception {
        farmaceuticoDao.update(farmaceutico);
    }

    // =============== PACIENTES ===============
    public void create(Paciente p) throws Exception {
        try {
            pacienteDao.read(p.getId());
            throw new Exception("Paciente ya existe");
        } catch (Exception e) {}
        pacienteDao.create(p);
    }

    public void updatePaciente(Paciente paciente) throws Exception {
        pacienteDao.update(paciente);
    }

    public void delete(Paciente p) throws Exception {
        pacienteDao.delete(p);
    }

    public Paciente read(Paciente p) throws Exception {
        return pacienteDao.read(p.getId());
    }

    public List<Paciente> findAllPaciente() {
        return pacienteDao.findAll();
    }

    public List<Paciente> searchPacienteByName(String name) {
        return pacienteDao.searchByName(name);
    }

    public List<Paciente> searchPacienteById(String id) {
        return pacienteDao.searchById(id);
    }

    // =============== USUARIOS ===============
    public void create(Usuario u) throws Exception {
        try {
            usuarioDao.read(u.getId());
            throw new Exception("Usuario ya existe");
        } catch (Exception e) {}
        usuarioDao.create(u);
    }

    public void updateUsuario(Usuario usuario) throws Exception {
        usuarioDao.update(usuario);
    }

    public Usuario read(Usuario u) throws Exception {
        return usuarioDao.read(u.getId());
    }

    public Usuario login(Usuario usuario) throws Exception {
        Usuario logged = usuarioDao.read(usuario.getId());
        if (!logged.getClave().equals(usuario.getClave())) {
            throw new Exception("Clave o ID no coinciden");
        }
        Sesion.setUsuario(logged);
        return logged;
    }

    public Usuario findUserById(String id) {
        try {
            return usuarioDao.read(id);
        } catch (Exception e) {
            return null;
        }
    }

    // =============== DEPARTAMENTOS ===============
    public List<Departamento> findAllDepartamentos() {
        return departamentoDao.findAll();
    }

    public List<Departamento> search(Departamento filtro) {
        return departamentoDao.findAll().stream()
                .filter(d -> d.getNombre().toLowerCase().contains(
                        filtro.getNombre() != null ? filtro.getNombre().toLowerCase() : ""
                ))
                .sorted(Comparator.comparing(Departamento::getNombre))
                .collect(Collectors.toList());
    }
}