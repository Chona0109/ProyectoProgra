package sistema.logic;

import sistema.data.data;
import sistema.data.XmlPersister;
import sistema.logic.entities.*;
import sistema.presentation.prescribirModificarDetalle.prescribirModificarDetalle;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Service {
    private static Service theInstance;
    private data data;
    private int recetaCounter = 1;

    public static Service instance() {
        if (theInstance == null) {
            theInstance = new Service();
        }
        return theInstance;
    }

    private Service() {
        try {
            data = XmlPersister.instance().load();
        } catch (Exception e) {
            data = new data();
        }
    }

    public void stop() {
        try {
            XmlPersister.instance().store(data);
        } catch (Exception e) {
            System.out.println(e);
        }
    }



    //Medicos
    public void create(Medico m) throws Exception {

        Medico existente = data.getMedicos().stream()
                .filter(med -> med.getId().equals(m.getId()))
                .findFirst()
                .orElse(null);
        if (existente != null) throw new Exception("Médico ya existe");

        Departamento dep = data.getDepartamentos().stream()
                .filter(d -> d.getCodigo().equals("002")) // 002 = Medico
                .findFirst()
                .orElseThrow(() -> new Exception("Departamento de médico no existe"));
        m.setDepartamento(dep);

        data.getMedicos().add(m);

        Usuario u = new Usuario();
        u.setId(m.getId());
        u.setClave(m.getId());
        u.setDepartamento(dep);

        Usuario userExistente = data.getUsuarios().stream()
                .filter(us -> us.getId().equals(u.getId()))
                .findFirst()
                .orElse(null);
        if (userExistente == null) {
            data.getUsuarios().add(u);
        }

        XmlPersister.instance().store(data);
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
        } else {
            throw new Exception("Médico no existe");
        }
    }

    public List<Medico> findAllMedicos() {
        return data.getMedicos();
    }


    //Medicamentos
    public void create(Medicamento m) throws Exception {
        Medicamento result = data.getMedicamentos().stream()
                .filter(med -> med.getCodigo().equals(m.getCodigo()))
                .findFirst()
                .orElse(null);
        if (result == null) {
            data.getMedicamentos().add(m);
        } else {
            throw new Exception("Medicamento ya existe");
        }
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

    public void delete(Medicamento m) throws Exception {
        Medicamento result = data.getMedicamentos().stream()
                .filter(med -> med.getCodigo().equals(m.getCodigo()))
                .findFirst()
                .orElse(null);
        if (result != null) {
            data.getMedicamentos().remove(result);
        } else {
            throw new Exception("Medicamento no existe");
        }
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

    //Recetas

    private String generarIdUnico() {
        int max = data.getRecetas().stream()
                .mapToInt(r -> {
                    try {
                        return Integer.parseInt(r.getId().replace("R", ""));
                    } catch (Exception e) {
                        return 0;
                    }
                })
                .max()
                .orElse(0);
        return "R" + (max + 1);
    }


    private boolean recetaYaExiste(Receta r) {
        return data.getRecetas().stream().anyMatch(existing ->
                existing.getPaciente().getId().equals(r.getPaciente().getId()) &&
                        existing.getMedico().getId().equals(r.getMedico().getId()) &&
                        existing.getFechaConfeccion().equals(r.getFechaConfeccion()) &&
                        existing.getMedicamentos().equals(r.getMedicamentos())
        );
    }



    public Receta createReceta(Receta r) throws Exception {
        if (r.getMedico() == null) {
            Usuario usuarioLogueado = Sesion.getUsuario();
            if (usuarioLogueado == null) {
                throw new Exception("No hay usuario logueado");
            }
            if (usuarioLogueado.getDepartamento() != null &&
                    usuarioLogueado.getDepartamento().getCodigo().equals("002")) {
                Medico m = data.getMedicos().stream()
                        .filter(med -> med.getId().equals(usuarioLogueado.getId()))
                        .findFirst()
                        .orElseThrow(() -> new Exception("Médico logueado no existe en la lista"));
                r.setMedico(m);
            } else {
                throw new Exception("Usuario no autorizado o médico no asignado");
            }
        }

        if (recetaYaExiste(r)) {
            throw new Exception("Ya existe una receta idéntica para este paciente, médico y fecha");
        }

        if (r.getId() == null || r.getId().isEmpty()) {
            r.setId(generarIdUnico());
        }

        data.getRecetas().add(r);
        XmlPersister.instance().store(data);
        return r;
    }


    public List<Receta> searchRecetaById(String id) {
        if (id == null || id.isEmpty()) {
            return findAllRecetas();
        }
        return data.getRecetas().stream()
                .filter(r -> r.getId() != null &&
                        r.getId().toLowerCase().contains(id.toLowerCase()))
                .collect(Collectors.toList());
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
    }


    public Receta readReceta(Receta r) throws Exception {
        Receta result = data.getRecetas().stream()
                .filter(rec -> rec.getId().equals(r.getId()))
                .findFirst()
                .orElse(null);
        if (result != null) return result;
        else throw new Exception("Receta no existe");
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

        XmlPersister.instance().store(data);
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

    public List<Receta> searchRecetaByIdPaciente(String idPaciente) {
        return data.getRecetas().stream()
                .filter(r -> r.getPaciente() != null &&
                        r.getPaciente().getId() != null &&
                        r.getPaciente().getId().toLowerCase().contains(idPaciente.toLowerCase()))
                .collect(Collectors.toList());
    }



    public void removeMedicamentoFromReceta(String recetaId, int index) throws Exception {
        Receta r = readReceta(new Receta(){{
            setId(recetaId);
        }});
        if (index >= 0 && index < r.getMedicamentos().size()) {
            r.getMedicamentos().remove(index);
        } else {
            throw new Exception("Índice de medicamento inválido");
        }
    }

    //Modificar detalle
    public Receta modificarDetalleMedicamento(Receta receta, int row) throws Exception {
        if (receta == null || receta.getMedicamentos() == null || row < 0 || row >= receta.getMedicamentos().size()) {
            throw new Exception("Índice inválido para modificar detalle");
        }

        MedicamentoDetalle detalle = receta.getMedicamentos().get(row);

        prescribirModificarDetalle detalleDialog = new prescribirModificarDetalle(null, detalle.getMedicamento());

        detalleDialog.setCantidad(detalle.getCantidad());
        detalleDialog.setDias(detalle.getDias());
        detalleDialog.setIndicaciones(detalle.getIndicaciones());

        detalleDialog.setVisible(true);

        if (detalleDialog.isGuardado()) {
            detalle.setCantidad(detalleDialog.getCantidad());
            detalle.setDias(detalleDialog.getDias());
            detalle.setIndicaciones(detalleDialog.getIndicaciones());
        }

        return receta;
    }


    //Farmaceutas
    public void create(Farmaceutico f) throws Exception {
        Farmaceutico existente = data.getFarmaceuticos().stream()
                .filter(far -> far.getId().equals(f.getId()))
                .findFirst()
                .orElse(null);
        if (existente != null) throw new Exception("Farmacéutico ya existe");

        Departamento dep = data.getDepartamentos().stream()
                .filter(d -> d.getCodigo().equals("003")) // 003 = Farmaceutico
                .findFirst()
                .orElseThrow(() -> new Exception("Departamento de farmacéutico no existe"));
        f.setDepartamento(dep);

        data.getFarmaceuticos().add(f);

        Usuario u = new Usuario();
        u.setId(f.getId());
        u.setClave(f.getId());
        u.setDepartamento(dep);

        Usuario userExistente = data.getUsuarios().stream()
                .filter(us -> us.getId().equals(u.getId()))
                .findFirst()
                .orElse(null);
        if (userExistente == null) {
            data.getUsuarios().add(u);
        }

        XmlPersister.instance().store(data);
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
                .sorted(Comparator.comparing(Farmaceutico::getNombre))
                .collect(Collectors.toList());
    }

    public void delete(Farmaceutico f) throws Exception {
        Farmaceutico result = data.getFarmaceuticos().stream()
                .filter(far -> far.getId().equals(f.getId()))
                .findFirst()
                .orElse(null);
        if (result != null) {
            data.getFarmaceuticos().remove(result);
        } else {
            throw new Exception("Farmacéutico no existe");
        }
    }

    //Paciente
    public void create(Paciente p) throws Exception {
        Paciente result = data.getPacientes().stream()
                .filter(pac -> pac.getId().equals(p.getId()))
                .findFirst()
                .orElse(null);
        if (result == null) {
            data.getPacientes().add(p);
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
        } else {
            throw new Exception("Paciente no existe");
        }
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
                .sorted(Comparator.comparing(Paciente::getNombre))
                .collect(Collectors.toList());
    }

    public List<Paciente> searchPacienteById(String id) {
        return data.getPacientes().stream()
                .filter(p -> p.getId().toLowerCase().contains(id.toLowerCase()))
                .sorted(Comparator.comparing(Paciente::getId))
                .collect(Collectors.toList());
    }

    //Usuario
    public void create(Usuario u) throws Exception {
        Usuario result = data.getUsuarios().stream()
                .filter(us -> us.getId().equals(u.getId()))
                .findFirst().orElse(null);
        if (result == null) {
            data.getUsuarios().add(u);
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

    public Usuario findUserById(String id) {
        for (Usuario u : data.getUsuarios()) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return null;
    }

    //Departamento

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


}