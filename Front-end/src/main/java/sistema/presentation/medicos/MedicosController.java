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

        try {
            socketListener = new SocketListener(this, Proxy.instance().getSid());
            socketListener.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Inicializamos la lista y departamentos
        try {
            model.setList(Proxy.instance().search(new Medico()));
            model.setDepartamentos(Proxy.instance().search(new Departamento()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ==================== SOCKET LISTENER ====================
    @Override
    public void deliver_message(String message) {
        System.out.println("Mensaje recibido: " + message);
        try {
            search(new Medico()); // refresca la lista al recibir mensaje
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if (socketListener != null) socketListener.stop();
    }
}
