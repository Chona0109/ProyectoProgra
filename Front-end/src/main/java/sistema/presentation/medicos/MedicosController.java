package sistema.presentation.medicos;

import sistema.logic.Proxy;
import logic.entities.*;
import sistema.logic.SocketListener;
import sistema.presentation.ThreadListener;

import javax.swing.*;
import java.util.List;

public class MedicosController implements ThreadListener {

    private MedicosModel model;
    private MedicosForm view;
    private SocketListener socketListener;

    public MedicosController(MedicosForm view, MedicosModel model) {
        this.view = view;
        this.model = model;

        model.init();

        view.setController(this);
        view.setModel(model);

        // ✅ Registrar este controlador en el SocketListener compartido
        try {
            socketListener = SocketListener.getInstance(this, Proxy.instance().getSid());
            System.out.println("✓ MedicosController registrado en SocketListener");
        } catch (Exception e) {
            System.err.println("❌ Error registrando SocketListener: " + e.getMessage());
            e.printStackTrace();
        }

        // Carga inicial de médicos y departamentos
        try {
            model.setList(Proxy.instance().search(new Medico()));
            model.setDepartamentos(Proxy.instance().search(new Departamento()));
            System.out.println("✓ Datos iniciales cargados en MedicosController");
        } catch (Exception e) {
            System.err.println("❌ Error cargando datos iniciales: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== SOCKET LISTENER ====================
    @Override
    public void deliver_message(String message) {
        System.out.println("🔔 [MEDICOS CONTROLLER] Mensaje recibido: " + message);

        // ✅ Recargar datos en un hilo separado para no bloquear UI
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("🔄 [MEDICOS CONTROLLER] Actualizando lista de médicos...");

                // Obtener lista actualizada del servidor
                List<Medico> medicosActualizados = Proxy.instance().search(new Medico());

                // Actualizar el modelo (esto dispara PropertyChange que actualiza la tabla)
                model.setList(medicosActualizados);
                System.out.println("✅ [MEDICOS CONTROLLER] Lista actualizada. Total: " + medicosActualizados.size());

            } catch (Exception e) {
                System.err.println("❌ [MEDICOS CONTROLLER] Error actualizando médicos: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    // ==================== MÉTODOS CRUD ====================
    public void search(Medico filter) throws Exception {
        model.setFilter(filter);
        List<Medico> rows = Proxy.instance().search(model.getFilter());
        model.setMode(MedicosModel.MODE_CREATE);
        model.setList(rows);
    }

    public void create(Medico e) throws Exception {
        Proxy.instance().create(e);
        model.setCurrent(new Medico());
        search(new Medico());
    }

    public void update(Medico e) throws Exception {
        Proxy.instance().updateMedico(e);
        search(new Medico());
        model.setCurrent(new Medico());
    }

    public void read(String id) throws Exception {
        Medico e = new Medico();
        e.setId(id);
        try {
            model.setCurrent(Proxy.instance().read(e));
        } catch (Exception ex) {
            Medico b = new Medico();
            b.setId(id);
            model.setCurrent(b);
            throw ex;
        }
    }

    public void delete(String id) throws Exception {
        Medico m = new Medico();
        m.setId(id);
        Proxy.instance().delete(m);
        model.setCurrent(new Medico());
        model.setList(Proxy.instance().search(new Medico()));
    }

    public void setCurrent(Medico e) {
        model.setCurrent(e);
        model.setMode(MedicosModel.MODE_EDIT);
    }

    public void clear() {
        model.setCurrent(new Medico());
        model.setMode(MedicosModel.MODE_CREATE);
    }

    // ==================== DEPARTAMENTOS ====================
    public void setDepartamento(int row) {
        if (row >= 0 && row < model.getDepartamentos().size()) {
            Departamento dep = model.getDepartamentos().get(row);
            model.setDepartamento(dep);
        }
    }

    public void searchDepartamentos(String nombre) {
        Departamento d = new Departamento();
        d.setNombre(nombre);
        model.setDepartamentos(Proxy.instance().search(d));
    }

    // ==================== STOP SOCKET ====================
    public void stop() {
        if (socketListener != null) {
            socketListener.stop();
            System.out.println("✓ SocketListener detenido para MedicosController");
        }
    }
}